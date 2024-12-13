package com.example.medical_appointment_booking_app.payload.request.Form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OtpForm {

    @NotBlank(message = "otp code must be not blank !")
    private String otpCode;
}
