package com.example.medical_appointment_booking_app.controller.admin;

import com.example.medical_appointment_booking_app.payload.request.Dto.UserDto;
import com.example.medical_appointment_booking_app.payload.request.Form.TimeScheduleForm;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import com.example.medical_appointment_booking_app.service.ScheduleService;
import com.example.medical_appointment_booking_app.service.TimeScheduleService;
import com.example.medical_appointment_booking_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

//@RestController
//@RequestMapping("api/v1/admin")
//public class AdminController {
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private TimeScheduleService timeScheduleService;
//    @Autowired
//    private ScheduleService scheduleService;
//
//    // lấy tất cả user
//    @GetMapping("/user")
//    public ResponseEntity<Page<UserDto>> getUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
//        return ResponseEntity.ok(userService.getUsers(page,size));
//    }
//
//    // tạo 1 TimeSchedule mới
//    @PostMapping("/time-schedule")
//    public ResponseEntity<ResponseData<String>> createTimeSchedule(@RequestBody TimeScheduleForm form) {
//        return ResponseEntity.ok(timeScheduleService.createTimeSchedule(form));
//    }
//
//    //bác sĩ chọn ca trực
//    @PostMapping("/schedule/{timeScheduleId}")
//    public ResponseEntity<ResponseData<String>> createSchedule(
//            Principal principal,
//            @PathVariable Long timeScheduleId) {
//        return ResponseEntity.ok(scheduleService.createSchedule(principal, timeScheduleId));
//    }
//}
