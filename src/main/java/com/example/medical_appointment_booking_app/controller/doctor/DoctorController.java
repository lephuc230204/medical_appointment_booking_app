//package com.example.medical_appointment_booking_app.controller.doctor;
//
//
//import com.example.medical_appointment_booking_app.payload.response.ResponseData;
//import com.example.medical_appointment_booking_app.service.ScheduleService;
//import com.example.medical_appointment_booking_app.service.TimeScheduleService;
//import com.example.medical_appointment_booking_app.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.security.Principal;
//
//@RestController
//@RequestMapping("api/v1/doctor")
//public class DoctorController {
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private TimeScheduleService timeScheduleService;
//    @Autowired
//    private ScheduleService scheduleService;
//
//
//    //bác sĩ chọn ca trực
//    @PostMapping("/{timeScheduleId}")
//    public ResponseEntity<ResponseData<String>> createSchedule(
//            Principal principal,
//            @PathVariable Long timeScheduleId) {
//        return ResponseEntity.ok(scheduleService.createSchedule(principal, timeScheduleId));
//    }
//}
