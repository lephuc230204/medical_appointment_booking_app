package com.example.medical_appointment_booking_app.payload.request.Form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserForm {
    private String username;
    @NotBlank(message = "Email not found")
    private String email;
    private String password;
    private String confirmPassword;
    private LocalDate dob;
    private String phoneNumber;
}
