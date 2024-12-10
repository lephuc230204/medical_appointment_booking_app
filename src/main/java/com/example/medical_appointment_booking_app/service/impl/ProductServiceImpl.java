package com.example.medical_appointment_booking_app.service.impl;

import com.example.medical_appointment_booking_app.entity.Category;
import com.example.medical_appointment_booking_app.entity.Product;
import com.example.medical_appointment_booking_app.payload.request.Dto.ProductDto;
import com.example.medical_appointment_booking_app.payload.request.Form.ProductForm;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import com.example.medical_appointment_booking_app.repository.CategoryRepository;
import com.example.medical_appointment_booking_app.repository.ProductRepository;
import com.example.medical_appointment_booking_app.service.CategoryService;
import com.example.medical_appointment_booking_app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;

    @Override
    public ResponseData<ProductDto> create(ProductForm form) {
        Category category =categoryService.checkOrCreateCategory(form.getCategoryName());

        Product product = Product.builder()
                .productName(form.getProductName())
                .category(category)
                .quantity(form.getQuantity())
                .currentQuantity(form.getQuantity())
                .price(form.getPrice())
                .description(form.getDescription())
                .build();
        productRepository.save(product);

        return new ResponseData<>(200, "Create new product successfully", ProductDto.fromEntity(product));
    }
}