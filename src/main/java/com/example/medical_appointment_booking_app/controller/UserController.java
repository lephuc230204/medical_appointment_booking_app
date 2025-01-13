package com.example.medical_appointment_booking_app.controller;

import com.example.medical_appointment_booking_app.payload.request.Dto.UserBasicDto;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import com.example.medical_appointment_booking_app.payload.response.ResponseError;
import com.example.medical_appointment_booking_app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag( name = "UserController", description = "API user quản lí thông tin ca nhan cua minh")
@Slf4j
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "Xem chi tiet tai khoan", description = "User co the xem chi tiet account cua minh")
    @GetMapping("/me")
    public ResponseEntity<ResponseData<UserBasicDto>> getMyInfo(){
        log.info("getMyInfo");
        return ResponseEntity.ok(userService.getMyInfo());
    }
}
