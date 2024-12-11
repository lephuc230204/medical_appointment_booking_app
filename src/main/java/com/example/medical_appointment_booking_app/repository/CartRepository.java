package com.example.medical_appointment_booking_app.repository;

import com.example.medical_appointment_booking_app.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser_UserId(Long userId);

}
