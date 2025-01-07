package com.example.medical_appointment_booking_app.controller;


import com.example.medical_appointment_booking_app.entity.Schedule;
import com.example.medical_appointment_booking_app.payload.request.Dto.ScheduleDto;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import com.example.medical_appointment_booking_app.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/v1/schedule")
@RestController
public class ScheduleController {
    private final ScheduleService scheduleService;

    @Operation( summary = "Tìm kiếm ca trực của 1 bác sĩ trong 1 ngày", description = "API cho phép người dùng tìm kiếm thời gian làm việc của 1 bác sĩ")
    @GetMapping("/{doctorId}")
    public ResponseEntity<ResponseData<List<ScheduleDto>>> getSchedule(@RequestParam LocalDate date, @PathVariable Long doctorId) {
        return ResponseEntity.ok(scheduleService.getByDoctorIdAndDate(doctorId, date));
    }
}
