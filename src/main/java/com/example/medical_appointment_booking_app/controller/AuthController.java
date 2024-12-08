package com.example.medical_appointment_booking_app.controller;

import com.example.medical_appointment_booking_app.payload.request.Dto.AuthDto;
import com.example.medical_appointment_booking_app.payload.request.Form.OtpForm;
import com.example.medical_appointment_booking_app.payload.request.Form.SignInForm;
import com.example.medical_appointment_booking_app.payload.request.Form.SignUpForm;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import com.example.medical_appointment_booking_app.payload.response.ResponseError;
import com.example.medical_appointment_booking_app.service.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthDto> signIn(@Valid @RequestBody SignInForm signInForm) {
        return ResponseEntity.ok(authService.login(signInForm));
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseData<String>> register(@RequestBody SignUpForm form){
        return ResponseEntity.ok(authService.register(form));}


    @PostMapping("/confirm/{userId}")
    public ResponseData<String> confirm(@PathVariable Long userId, @RequestBody OtpForm form) {
        log.info("Confirm user, userId={}, otpCode={}", userId, form.getOtpCode());

        try {
            authService.confirmUser(userId, form.getOtpCode());
            return new ResponseData<>(200, "User has confirmed successfully");
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError(400, "Confirm was failed");
        }
    }
}
