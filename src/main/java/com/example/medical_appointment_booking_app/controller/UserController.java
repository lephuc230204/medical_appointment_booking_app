//package com.example.medical_appointment_booking_app.controller;
//
//import com.example.medical_appointment_booking_app.payload.request.Dto.UserDto;
//import com.example.medical_appointment_booking_app.payload.request.Form.UserForm;
//import com.example.medical_appointment_booking_app.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("api/v1/users")
//public class UserController {
//    @Autowired
//    private UserService userService;
//
//    @GetMapping("")
//    public ResponseEntity<Page<UserDto>> getUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
//        return ResponseEntity.ok(userService.getUsers(page,size));
//    }
//    @PostMapping("")
//    public ResponseEntity<UserDto> createUser(@RequestBody UserForm form){
//        return ResponseEntity.ok(userService.createUser(form));
//    }
//}
