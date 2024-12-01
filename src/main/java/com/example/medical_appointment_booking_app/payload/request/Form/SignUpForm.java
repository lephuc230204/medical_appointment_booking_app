package com.example.medical_appointment_booking_app.payload.request.Form;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SignUpForm {
    private String username;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dob;
    private String email;
    private String password;
    private String phoneNumber;
    private String confirmPassword;

}