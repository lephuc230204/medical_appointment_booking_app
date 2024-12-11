package com.example.medical_appointment_booking_app.service;

import com.example.medical_appointment_booking_app.payload.request.Dto.CartDto;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;

public interface CartService {
    ResponseData<CartDto> getCartByUserId();
}
