package com.example.medical_appointment_booking_app.service.impl;

import com.example.medical_appointment_booking_app.entity.Category;
import com.example.medical_appointment_booking_app.repository.CategoryRepository;
import com.example.medical_appointment_booking_app.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category checkOrCreateCategory(String categoryName) {
        Category category = categoryRepository.findByCategoryName(categoryName).orElse(null);
        if (category == null) {
            category = new Category();
            category.setCategoryName(categoryName);
            categoryRepository.save(category);
        }
        return category;
    }
}
