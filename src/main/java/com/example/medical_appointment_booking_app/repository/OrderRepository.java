package com.example.medical_appointment_booking_app.repository;

import com.example.medical_appointment_booking_app.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
