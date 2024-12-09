package com.example.medical_appointment_booking_app.payload.request.Dto;

import com.example.medical_appointment_booking_app.entity.Product;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductDto {
    private Long productId;
    private String productName;
    private String category;
    private Long quantity;
    private Long currentQuantity;
    private Double price;
    private String description;

    public static ProductDto fromEntity(Product product) {
        return ProductDto.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .category(product.getCategory().getCategoryName())
                .quantity(product.getQuantity())
                .currentQuantity(product.getCurrentQuantity())
                .price(product.getPrice())
                .build();
    }
}
