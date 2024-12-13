package com.example.medical_appointment_booking_app.service.impl;

import com.example.medical_appointment_booking_app.entity.Cart;
import com.example.medical_appointment_booking_app.entity.Role;
import com.example.medical_appointment_booking_app.entity.User;
import com.example.medical_appointment_booking_app.payload.request.Dto.AuthDto;
import com.example.medical_appointment_booking_app.payload.request.Form.SignInForm;
import com.example.medical_appointment_booking_app.payload.request.Form.SignUpForm;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import com.example.medical_appointment_booking_app.payload.response.ResponseError;
import com.example.medical_appointment_booking_app.repository.CartRepository;
import com.example.medical_appointment_booking_app.repository.RoleRepository;
import com.example.medical_appointment_booking_app.repository.UserRepository;
import com.example.medical_appointment_booking_app.middleware.JwtProvider;
import com.example.medical_appointment_booking_app.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public AuthDto login(SignInForm form) {
        // Kiểm tra xem người dùng có tồn tại không
        User user = userRepository.findByEmail(form.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + form.getEmail()));

        if (!user.getStatus().equals(User.Status.ACTIVE)) {
            throw new IllegalArgumentException("Account is not active");
        }
        // Thực hiện xác thực
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(form.getEmail(), form.getPassword())
            );
        } catch (AuthenticationException e) {
            log.error("Authentication failed for email: {} with exception: {}", form.getEmail(), e.getMessage());
            throw new IllegalArgumentException("Invalid email or password");
        }
        String accessToken = jwtProvider.generateToken(authentication);
        log.info("User {} logged in successfully with ", user.getEmail());
        String status = "success";
        String result = "Login successful";

        return AuthDto.from(user, accessToken, status, result);
    }

    @Override
    public ResponseData<String> register(SignUpForm form) {
        if (userRepository.existsByEmail(form.getEmail())) {
            return new ResponseError<>(400, "Email address already in use");
        }
        Role role = roleRepository.findByName("ROLE_USER").orElse(null);
        if (role == null) {
            log.error("Role not found");
            return new ResponseError<>(400, "Role not found");
        }
        String otpCode = String.format("%06d", new Random().nextInt(99999));
        User user = User.builder()
                .username(form.getUsername())
                .email(form.getEmail())
                .password(passwordEncoder.encode(form.getPassword()))
                .role(role)
                .otp(otpCode)
                .dob(form.getDob())
                .phoneNumber(form.getPhoneNumber())
                .status(User.Status.NONACTIVE)
                .build();
        userRepository.save(user);

        Cart cart = Cart.builder()
                .user(user)
                .createdDate(LocalDate.now())
                .build();
        cartRepository.save(cart);

        kafkaTemplate.send("confirm-account-topic", String.format("email=%s,id=%s,otpCode=%s", user.getEmail(), user.getUserId(), otpCode));
        log.info("User {} registered successfully with ID {}, pls check email to confirm OTP. Thanks!", user.getEmail(), user.getUserId());
        return new ResponseData<>(200, "Success register new user. Please check your email for confirmation", "Id: " + user.getUserId());
    }


    @Override
    public ResponseData<String> confirmUser(long userId, String otpCode) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Check if the OTP matches
        if (!otpCode.equals(user.getOtp())) {
            log.error("OTP does not match for userId={}", userId);
            throw new IllegalArgumentException("OTP is incorrect");
        }

        user.setStatus(User.Status.ACTIVE);
        user.setSetCreatedDate(LocalDate.now());
        userRepository.save(user);
        return new ResponseData<>(200, "confirm successfully");
    }
}
