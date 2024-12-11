package com.example.medical_appointment_booking_app.service;

import com.example.medical_appointment_booking_app.payload.request.Dto.ProductDto;
import com.example.medical_appointment_booking_app.payload.request.Form.ProductForm;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import org.springframework.data.domain.Page;

import java.io.IOException;

public interface ProductService {
    ResponseData<ProductDto> create(ProductForm form) throws IOException;
    ResponseData<Page<ProductDto>> getProduct(int page, int size);
    ResponseData<ProductDto> getProductById(Long productId);
    ResponseData<ProductDto> updateProduct(Long productId, ProductForm form) throws IOException;
    ResponseData<Void> deleteProduct(Long productId);
}
