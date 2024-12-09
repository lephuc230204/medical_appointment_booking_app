package com.example.medical_appointment_booking_app.payload.request.Form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignInForm {
    private String email;
    private String password;

}
