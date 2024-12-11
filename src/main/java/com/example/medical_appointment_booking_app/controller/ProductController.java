package com.example.medical_appointment_booking_app.controller;

import com.example.medical_appointment_booking_app.payload.request.Dto.ProductDto;
import com.example.medical_appointment_booking_app.payload.request.Form.ProductForm;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import com.example.medical_appointment_booking_app.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("api/v1/products")
@RestController
public class ProductController {

    private final ProductService productService;

    @PostMapping("")
    public ResponseEntity<ResponseData<?>> create(@ModelAttribute @Valid ProductForm form) throws IOException {
        return ResponseEntity.ok(productService.create(form));
    }

    @GetMapping("")
    public ResponseEntity<ResponseData<Page<ProductDto>>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(productService.getProduct(page, size));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ResponseData<ProductDto>> getProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }
}
