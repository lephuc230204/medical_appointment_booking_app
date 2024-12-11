package com.example.medical_appointment_booking_app.service.impl;

import com.example.medical_appointment_booking_app.entity.Cart;
import com.example.medical_appointment_booking_app.entity.User;
import com.example.medical_appointment_booking_app.payload.request.Dto.CartDto;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import com.example.medical_appointment_booking_app.payload.response.ResponseError;
import com.example.medical_appointment_booking_app.repository.CartItemRepository;
import com.example.medical_appointment_booking_app.repository.CartRepository;
import com.example.medical_appointment_booking_app.repository.UserRepository;
import com.example.medical_appointment_booking_app.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseData<CartDto> getCartByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            log.error("User not found for email: {}", email);
            return new ResponseError<>(404, "User not found");
        }

        Cart cart = cartRepository.findByUser_UserId(user.getUserId());

        return new ResponseData<>(200,"Get cart successfully", CartDto.toDto(cart));
    }
}
