package com.example.medical_appointment_booking_app.payload.request.Dto;

import com.example.medical_appointment_booking_app.entity.Order;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class OrderDto {
    private Long orderId;
    private String userName;
    private String address;
    private String phoneNumber;
    private Double totalAmount;
    private LocalDate orderDate;
    private Order.Status orderStatus;
    private Order.Payment ordrePayment;

    public static OrderDto toDto(Order order) {
        return OrderDto.builder()
                .orderId(order.getOrderId())
                .userName(order.getUser().getUsername())
                .address(order.getAddress())
                .phoneNumber(order.getPhoneNumber())
                .totalAmount(order.getTotalAmount())
                .orderDate(order.getOrderDate())
                .orderStatus(order.getStatus())
                .ordrePayment(order.getPaymentMethod())
                .build();
    }
}
