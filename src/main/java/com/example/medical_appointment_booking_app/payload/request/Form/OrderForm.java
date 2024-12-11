package com.example.medical_appointment_booking_app.payload.request.Form;

import com.example.medical_appointment_booking_app.entity.Order;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class OrderForm {

    @NotNull(message = "Address cannot be null")
    private String address;

    @NotNull(message = "Phone number cannot be null")
    @Size(min = 10, message = "Phone number must be at least 11 characters long")
    private String phoneNumber;

    private List<Long> cartItemId;
    private Order.Payment payment;

}
