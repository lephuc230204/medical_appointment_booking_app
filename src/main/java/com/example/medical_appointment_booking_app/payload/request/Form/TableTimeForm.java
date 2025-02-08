package com.example.medical_appointment_booking_app.payload.request.Form;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TableTimeForm {

    private LocalDate startDate;

    private LocalDate endDate;
}
