package com.example.medical_appointment_booking_app.payload.request.Form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class ProductForm {

    @NotBlank(message = "Product name must not be blank")
    private String productName;

    @NotBlank(message = "Category name must not be blank")
    private String categoryName;

    @NotNull(message = "Product image must not be null")
    private MultipartFile productImage;

    @NotNull(message = "Quantity must not be null")
    @Min(value = 1, message = "Quantity must be greater than 0")
    private int quantity;

    @NotNull(message = "Price must not be null")
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private BigDecimal price;

    @NotBlank(message = "Description must not be null or empty")
    private String description;
}
