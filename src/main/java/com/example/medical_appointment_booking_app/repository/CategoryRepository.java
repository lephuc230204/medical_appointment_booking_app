package com.example.medical_appointment_booking_app.repository;

import com.example.medical_appointment_booking_app.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    Optional<Category> findByCategoryName(String categoryName);
}
