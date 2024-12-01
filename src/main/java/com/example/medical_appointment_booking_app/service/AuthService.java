package com.example.medical_appointment_booking_app.service;

import com.example.medical_appointment_booking_app.payload.request.Dto.AuthDto;
import com.example.medical_appointment_booking_app.payload.request.Form.SignInForm;
import com.example.medical_appointment_booking_app.payload.request.Form.SignUpForm;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;


public interface AuthService {
    AuthDto login(SignInForm form);
    ResponseData<String> register(SignUpForm form);
//    ResponseData<String> logout(HttpServletRequest request, HttpServletResponse response);
}
