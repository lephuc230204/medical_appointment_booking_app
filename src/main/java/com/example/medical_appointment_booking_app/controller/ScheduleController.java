package com.example.medical_appointment_booking_app.controller;


import com.example.medical_appointment_booking_app.entity.Schedule;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import com.example.medical_appointment_booking_app.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@RequestMapping("api/v1/schedule")
@RestController
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/{timeScheduleId}")
    public ResponseEntity<ResponseData<String>> createSchedule(
            Principal principal,
            @PathVariable Long timeScheduleId) {
        return ResponseEntity.ok(scheduleService.createSchedule(principal, timeScheduleId));
    }
}
