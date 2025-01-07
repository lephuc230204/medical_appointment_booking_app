package com.example.medical_appointment_booking_app.controller;


import com.example.medical_appointment_booking_app.entity.Appointment;
import com.example.medical_appointment_booking_app.payload.request.Dto.AppointmentDto;
import com.example.medical_appointment_booking_app.payload.request.Form.AppointmentForm;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import com.example.medical_appointment_booking_app.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation( summary = "Tạo 1 yêu cầu đặt khám ", description = "API cho phép người dùng tạo 1 yêu cầu đặt khám ")
    @PostMapping
    private ResponseEntity<ResponseData<AppointmentDto>> createRequest(@RequestBody AppointmentForm appointmentForm, Principal principal) {
        return ResponseEntity.ok(appointmentService.createRequest(appointmentForm, principal));
    }

    @Operation( summary = "Huỷ yêu cầu đặt khám ", description = "API cho phép người dùng Huỷ yêu cầu đặt khám")
    @PostMapping("/cancel/{appointmentId}")
    private  ResponseEntity<ResponseData<String>> cancelRequest(@PathVariable Long appointmentId, Principal principal) {
        return ResponseEntity.ok(appointmentService.cancelRequest(appointmentId,principal));
    }

    @Operation( summary = "Xem các yêu cầu đã gửi", description = "API cho phép người dùng Xem các yêu cầu đã gửi")
    @GetMapping("/me")
    private ResponseEntity<ResponseData<List<AppointmentDto>>> getMyRequests(Principal principal) {
        return ResponseEntity.ok(appointmentService.getMyAppointments(principal));
    }
}
