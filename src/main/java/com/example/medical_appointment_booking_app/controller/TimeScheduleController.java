package com.example.medical_appointment_booking_app.controller;


import com.example.medical_appointment_booking_app.entity.TimeSchedule;
import com.example.medical_appointment_booking_app.payload.request.Dto.TimeScheduleDto;
import com.example.medical_appointment_booking_app.payload.request.Form.TimeScheduleForm;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import com.example.medical_appointment_booking_app.service.TimeScheduleService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation( summary = "Lấy tất cả các ca làm việc trong ngày", description = "API cho phép người dùng xem thời gian làm việc của bác sĩ trong ngày ")
    @GetMapping("/get-by-date")
    public ResponseEntity<ResponseData<List<TimeScheduleDto>>> getAllTimeSchedules(@RequestParam LocalDate date) {
        return ResponseEntity.ok(timeScheduleService.getByDate(date));
    }
}
