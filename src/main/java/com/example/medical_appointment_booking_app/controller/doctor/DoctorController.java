package com.example.medical_appointment_booking_app.controller.doctor;


import com.example.medical_appointment_booking_app.payload.request.Dto.AppointmentDto;
import com.example.medical_appointment_booking_app.payload.request.Dto.ScheduleDto;
import com.example.medical_appointment_booking_app.payload.request.Dto.TableTimeDto;
import com.example.medical_appointment_booking_app.payload.request.Form.TableTimeForm;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import com.example.medical_appointment_booking_app.service.ScheduleService;
import com.example.medical_appointment_booking_app.service.TimeScheduleService;
import com.example.medical_appointment_booking_app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.kafka.shaded.io.opentelemetry.proto.resource.v1.ResourceProto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/doctor")
public class DoctorController {
    @Autowired
    private UserService userService;
    @Autowired
    private TimeScheduleService timeScheduleService;
    @Autowired
    private ScheduleService scheduleService;


    @Operation( summary = "bác sĩ chọn ca làm việc", description = "API cho phép bác sĩ chọn ca làm việc")
    @PostMapping("/{timeScheduleId}")
    public ResponseEntity<ResponseData<String>> createSchedule(
            Principal principal,
            @PathVariable Long timeScheduleId) {
        return ResponseEntity.ok(scheduleService.createSchedule(principal, timeScheduleId));
    }

    @Operation( summary = "lịch ngày", description = "xem lịch trong ngày")
    @GetMapping("/me")
    public ResponseEntity<ResponseData<List<ScheduleDto>>> getMySchedule (Principal principal,
                                                                          @RequestParam LocalDate date) {
        return ResponseEntity.ok(scheduleService.getMySchedule(principal, date));
    }

    @Operation( summary = "check ca làm việc", description = "xem chi tiết ca làm việc")
    @GetMapping("/schedule/appointment/{scheduleId}")
    public ResponseEntity<ResponseData<List<AppointmentDto>>> getListAppointmentInSchedule (@PathVariable Long scheduleId) {
        return ResponseEntity.ok(scheduleService.getListAppointmentInSchedule(scheduleId));
    }

    @Operation( summary = "lịck tuần", description = "xem chi tiết lịch trong tuần ")
    @PostMapping("/schedule/inWeek")
    public ResponseEntity<ResponseData<List<TableTimeDto>>> getMyScheduleInWeek(Principal principal,@RequestBody TableTimeForm form){
        return ResponseEntity.ok(scheduleService.getMyScheduleInWeek(principal, form));
    }


}
