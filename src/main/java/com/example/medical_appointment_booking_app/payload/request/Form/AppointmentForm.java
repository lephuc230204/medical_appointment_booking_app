package com.example.medical_appointment_booking_app.payload.request.Form;

import lombok.Data;

@Data
public class AppointmentForm {

    private Long scheduleId;
    private String reason;
}
