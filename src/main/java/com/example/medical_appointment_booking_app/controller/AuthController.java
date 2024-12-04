package com.example.medical_appointment_booking_app.controller;

import com.example.medical_appointment_booking_app.payload.request.Dto.AuthDto;
import com.example.medical_appointment_booking_app.payload.request.Form.SignInForm;
import com.example.medical_appointment_booking_app.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthDto> signIn(@Valid @RequestBody SignInForm signInForm) {
        return ResponseEntity.ok(authService.login(signInForm));
    }

}
