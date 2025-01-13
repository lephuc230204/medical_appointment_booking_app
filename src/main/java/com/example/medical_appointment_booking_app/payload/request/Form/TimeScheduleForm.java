package com.example.medical_appointment_booking_app.payload.request.Form;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.time.LocalDate;

@Data
public class TimeScheduleForm {
    @NotNull(message = "Date cannot be null")
    @FutureOrPresent(message = "Date must be today or in the future")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;
}
