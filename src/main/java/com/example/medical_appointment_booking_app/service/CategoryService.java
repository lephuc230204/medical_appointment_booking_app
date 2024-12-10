package com.example.medical_appointment_booking_app.service;

import com.example.medical_appointment_booking_app.entity.Category;

public interface CategoryService {
    Category checkOrCreateCategory(String categoryName);
}
