package com.example.medical_appointment_booking_app.controller;

import com.example.medical_appointment_booking_app.payload.request.Dto.CartDto;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import com.example.medical_appointment_booking_app.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("api/v1/cart")
@RestController
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<ResponseData<CartDto>> getCartByUserId(){
        return ResponseEntity.ok(cartService.getCartByUserId());
    }
}
