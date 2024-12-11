package com.example.medical_appointment_booking_app.service;

import com.example.medical_appointment_booking_app.entity.CartItem;
import com.example.medical_appointment_booking_app.entity.Order;
import com.example.medical_appointment_booking_app.entity.OrderItem;
import com.example.medical_appointment_booking_app.payload.request.Dto.OrderItemDto;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;

import java.util.List;

public interface OrderItemService {

    List<OrderItem> create(List<CartItem> selectedCartItems, Order order);

    ResponseData<List<OrderItemDto>> getOrderItems();

    ResponseData<List<OrderItemDto>> getOrderItemsByOrderId(Long orderId);
}
