package com.example.medical_appointment_booking_app.payload.request.Form;

import com.example.medical_appointment_booking_app.entity.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class ProductForm {
    @NotBlank(message = "Product name must not be null")
    private String productName;

    @NotBlank(message = "Category must not be null")
    private Category category;

    @NotBlank(message = "Quantity must not be null")
    @Min(value = 1, message = "Quantity must be greater than 0")
    private Long quantity;

    @NotBlank(message = "Price must not be null")
    @Min(value = 0, message = "Price must be greater than 1")
    private Double price;

    @NotBlank(message = "Description must not be null or empty")
    private String description;

}
