package com.example.medical_appointment_booking_app.repository;

import com.example.medical_appointment_booking_app.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


}
