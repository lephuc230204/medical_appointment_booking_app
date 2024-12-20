package com.example.medical_appointment_booking_app.payload.request.Form;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddressForm {
    @NotNull(message = "provinceId must be not null! ")
    private int provinceId;

    @NotNull(message = "districtId must be not null! ")
    private int districtId;

    @NotBlank(message = "wardId must be not blank! ")
    private String wardId;

    @NotBlank(message = "fullAddress must be not blank! ")
    private String fullAddress;
}
