package com.example.medical_appointment_booking_app.service.impl;

import com.example.medical_appointment_booking_app.entity.Role;
import com.example.medical_appointment_booking_app.entity.User;
import com.example.medical_appointment_booking_app.payload.request.Dto.UserBasicDto;
import com.example.medical_appointment_booking_app.payload.request.Dto.UserDto;
import com.example.medical_appointment_booking_app.payload.request.Form.UserForm;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import com.example.medical_appointment_booking_app.repository.RoleRepository;
import com.example.medical_appointment_booking_app.repository.UserRepository;
import com.example.medical_appointment_booking_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Page<UserDto> getUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userRepository.findAll(pageable);
        Page<UserDto> userDtoPage = users.map(UserDto::toDto);
        return userDtoPage;
    }

    @Override
    public UserDto createUser(UserForm form) {
        User user = userRepository.findByEmail(form.getEmail()).
                orElseThrow(() -> new IllegalArgumentException("User is exist with email: " + form.getEmail()));
        if(!form.getConfirmPassword().equals(form.getPassword())){
            throw new IllegalArgumentException("Passwords do not match");
        }
        Role role = roleRepository.findByName("ROLE_USER").orElse(null);
        User newUser = User.builder()
                .username(form.getUsername())
                .email(form.getEmail())
                .password(form.getPassword())
                .phoneNumber(form.getPhoneNumber())
                .dob(form.getDob())
                .role(role)
                .status(User.Status.NONACTIVE)
                .build();
        userRepository.save(newUser);
        UserDto userDto = UserDto.toDto(newUser);
        return userDto;

    }

    @Override
    public ResponseData<UserDto> getUser(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return new ResponseData<>(200,"Get user success with id: "+id, UserDto.toDto(user));
    }

    @Override
    public ResponseData<UserBasicDto> getMyInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));

        return new ResponseData<>(200,"Get user success with email: "+email, UserBasicDto.fromUser(user));
    }
}