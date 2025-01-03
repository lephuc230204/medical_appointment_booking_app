package com.example.medical_appointment_booking_app.payload.request.Form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CartItemRequest {
    @NotNull(message = "Cart-item ID must be not null ")
    private Long cartItemId;
    @Size(min = 1, message = "quantity must be greater than 1 ")
    private int quantity;
}
