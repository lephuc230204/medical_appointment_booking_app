package com.example.medical_appointment_booking_app.controller;


import com.example.medical_appointment_booking_app.entity.Appointment;
import com.example.medical_appointment_booking_app.payload.request.Dto.AppointmentDto;
import com.example.medical_appointment_booking_app.payload.request.Form.AppointmentForm;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import com.example.medical_appointment_booking_app.service.AppointmentService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    private ResponseEntity<ResponseData<AppointmentDto>> createRequest(@RequestBody AppointmentForm appointmentForm, Principal principal) {
        return ResponseEntity.ok(appointmentService.createRequest(appointmentForm, principal));
    }
}
