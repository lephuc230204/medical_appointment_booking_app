package com.example.medical_appointment_booking_app.payload.request.Dto;

import com.example.medical_appointment_booking_app.entity.OrderItem;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class OrderItemDto {
    private Long orderItemId;
    private Long productId;
    private int quantity;
    private Double price;
    private Long orderId;

    public static OrderItemDto toDto(OrderItem orderItem) {
        return OrderItemDto.builder()
                .orderItemId(orderItem.getOrderItemId())
                .productId(orderItem.getProduct().getProductId())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getProduct().getPrice().doubleValue())
                .orderId(orderItem.getOrder().getOrderId())
                .build();
    }
}
