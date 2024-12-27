package com.example.medical_appointment_booking_app.payload.request.Dto;

import com.example.medical_appointment_booking_app.entity.Order;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class OrderDto {
    private Long orderId;
    private String userName;
    private int provinceId;
    private int districtId;
    private String wardId;
    private String fullAddress;
    private String phoneNumber;
    private Double totalPrice;
    private LocalDate orderDate;
    private double shippingFee;
    private String shippingUnit;
    private Order.Status orderStatus;
    private Order.Payment ordrePayment;
    private List<OrderItemDto> orderItems;

    public static OrderDto fromEntity(Order order) {
        return OrderDto.builder()
                .orderId(order.getOrderId())
                .userName(order.getUser().getUsername())
                .provinceId(order.getAddress().getProvinceId())
                .districtId(order.getAddress().getDistrictId())
                .wardId(order.getAddress().getWardId())
                .fullAddress(order.getAddress().getFullAddress())
                .phoneNumber(order.getPhone())
                .orderItems(order.getOrderItems().stream()
                        .map(OrderItemDto::fromEntity)
                        .collect(Collectors.toList()))
                .shippingFee(order.getShippingFee())
                .totalPrice(order.getTotalPrice())
                .orderDate(order.getOrderDate())
                .orderStatus(order.getStatus())
                .ordrePayment(order.getPaymentMethod())
                .build();
    }
}
