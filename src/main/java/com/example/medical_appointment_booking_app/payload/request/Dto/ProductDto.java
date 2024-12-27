package com.example.medical_appointment_booking_app.payload.request.Dto;

import com.example.medical_appointment_booking_app.entity.Product;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class ProductDto {
    private Long productId;
    private String productName;
    private String image;
    private String category;
    private int quantity;
    private int currentQuantity;
    private double price;
    private int weight;
    private String description;

    public static ProductDto fromEntity(Product product) {
        return ProductDto.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .image(product.getImage())
                .category(product.getCategory().getCategoryName())
                .quantity(product.getQuantity())
                .currentQuantity(product.getCurrentQuantity())
                .price(product.getPrice())
                .weight(product.getWeight())
                .description(product.getDescription())
                .build();
    }
}
