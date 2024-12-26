package com.example.medical_appointment_booking_app.service;

import com.example.medical_appointment_booking_app.entity.Address;
import com.example.medical_appointment_booking_app.entity.OrderItem;

import java.util.List;

public interface ShipFeeService {
    int calculateShipFee(List<OrderItem> orderItems, Address address);
}
