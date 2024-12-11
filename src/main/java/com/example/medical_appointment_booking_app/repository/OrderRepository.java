package com.example.medical_appointment_booking_app.repository;

import com.example.medical_appointment_booking_app.entity.Order;
import com.example.medical_appointment_booking_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);


}
