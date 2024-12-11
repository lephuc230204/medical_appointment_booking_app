package com.example.medical_appointment_booking_app.payload.request.Dto;


import com.example.medical_appointment_booking_app.entity.Cart;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class CartDto {
    private Long cartId;
    private Long userId;
    private LocalDate createdDate;

    public static CartDto toDto(Cart cart) {;
        return CartDto.builder()
                .cartId(cart.getCartId())
                .userId(cart.getUser().getUserId())
                .createdDate(LocalDate.now())
                .build();
    }
}
