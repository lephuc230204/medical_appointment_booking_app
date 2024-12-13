package com.example.medical_appointment_booking_app.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class MomoCallbackController {

    @PostMapping("/notify")
    public String handleNotify(@RequestBody Map<String, Object> response) {
        // Xử lý kết quả giao dịch từ MoMo
        System.out.println("Notify response: " + response);
        return "OK";
    }

    @GetMapping("/return")
    public String handleReturn(@RequestParam Map<String, String> queryParams) {
        // Hiển thị kết quả giao dịch cho người dùng
        System.out.println("Return query params: " + queryParams);
        return "Payment Successful!";
    }
}
