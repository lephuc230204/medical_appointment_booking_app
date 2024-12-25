package com.example.medical_appointment_booking_app.controller;


import com.example.medical_appointment_booking_app.entity.TimeSchedule;
import com.example.medical_appointment_booking_app.payload.request.Dto.TimeScheduleDto;
import com.example.medical_appointment_booking_app.payload.request.Form.TimeScheduleForm;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import com.example.medical_appointment_booking_app.service.TimeScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/v1/time-schedule")
@RestController
public class TimeScheduleController {
    private final TimeScheduleService timeScheduleService;

    @PostMapping
    public ResponseEntity<ResponseData<String>> createTimeSchedule(@RequestBody TimeScheduleForm form) {
        return ResponseEntity.ok(timeScheduleService.createTimeSchedule(form));
    }

    @GetMapping("/get-by-date")
    public ResponseEntity<ResponseData<List<TimeScheduleDto>>> getAllTimeSchedules(@RequestParam LocalDate date) {
        return ResponseEntity.ok(timeScheduleService.getByDate(date));
    }
}
