package com.example.medical_appointment_booking_app.payload.request.Dto;

import com.example.medical_appointment_booking_app.entity.CartItem;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class CartItemDto {
    private Long cartItemId;
    private Long cartId;
    private Long productId;
    private String productName;
    private double productPrice;
    private int quantity;
    private String image;
    private LocalDate createDate;

    public static CartItemDto toDto(CartItem cartItem) {
        return CartItemDto.builder()
                .cartItemId(cartItem.getCartItemId())
                .cartId(cartItem.getCart().getCartId())
                .productId(cartItem.getProduct().getProductId())
                .productName(cartItem.getProduct().getProductName())
                .productPrice(cartItem.getPrice())
                .quantity(cartItem.getQuantity())
                .image(cartItem.getProduct().getImage())
                .createDate(cartItem.getCreateDate())
                .build();
    }
}
