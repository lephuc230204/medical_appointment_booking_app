package com.example.medical_appointment_booking_app.controller;


import com.example.medical_appointment_booking_app.entity.TimeSchedule;
import com.example.medical_appointment_booking_app.payload.request.Form.TimeScheduleForm;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import com.example.medical_appointment_booking_app.service.TimeScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("api/v1/time-schedule")
@RestController
public class TimeScheduleController {
    private final TimeScheduleService timeScheduleService;

    @PostMapping
    public ResponseEntity<ResponseData<String>> createTimeSchedule(@RequestBody TimeScheduleForm form) {
        return ResponseEntity.ok(timeScheduleService.createTimeSchedule(form));
    }
}
