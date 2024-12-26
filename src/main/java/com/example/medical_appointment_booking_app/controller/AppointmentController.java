package com.example.medical_appointment_booking_app.controller;


import com.example.medical_appointment_booking_app.entity.Appointment;
import com.example.medical_appointment_booking_app.payload.request.Dto.AppointmentDto;
import com.example.medical_appointment_booking_app.payload.request.Form.AppointmentForm;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import com.example.medical_appointment_booking_app.service.AppointmentService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.shaded.io.opentelemetry.proto.resource.v1.ResourceProto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    private ResponseEntity<ResponseData<AppointmentDto>> createRequest(@RequestBody AppointmentForm appointmentForm, Principal principal) {
        return ResponseEntity.ok(appointmentService.createRequest(appointmentForm, principal));
    }

    @PostMapping("/cancel/{appointmentId}")
    private  ResponseEntity<ResponseData<String>> cancelRequest(@PathVariable Long appointmentId, Principal principal) {
        return ResponseEntity.ok(appointmentService.cancelRequest(appointmentId,principal));
    }

    @GetMapping("/me")
    private ResponseEntity<ResponseData<List<AppointmentDto>>> getMyRequests(Principal principal) {
        return ResponseEntity.ok(appointmentService.getMyAppointments(principal));
    }
}
