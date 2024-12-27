package com.example.medical_appointment_booking_app.payload.request.Dto;


import com.example.medical_appointment_booking_app.entity.OrderItem;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemDto {
    private Long orderItemId;
    private String bookName;
    private int weight;
    private int quantity;
    private double price;

    public static OrderItemDto fromEntity(OrderItem orderItem) {
        return OrderItemDto.builder()
                .orderItemId(orderItem.getOrderItemId())
                .bookName(orderItem.getProduct().getProductName())
                .weight(orderItem.getProduct().getWeight())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .build();
    }
}
