package com.example.medical_appointment_booking_app.controller;

import com.example.medical_appointment_booking_app.service.MomoPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/payment")
public class PaymentController {

    @Autowired
    private MomoPaymentService momoPaymentService;

    @PostMapping("/create")
    public ResponseEntity<String> createPayment(@RequestParam(required = true) Long orderId,
                                                @RequestParam(required = true) String orderInfo,
                                                @RequestParam(required = true) long amount) {

        if (orderId == null || orderInfo == null || orderInfo.isBlank() || amount <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tham số đầu vào không hợp lệ!");
        }

        try {
            String returnUrl = "https://2056-1-52-111-143.ngrok-free.app/api/v1/return";
            String notifyUrl = "https://2056-1-52-111-143.ngrok-free.app/api/v1/notify";

            // Gọi service để tạo URL thanh toán
            String paymentUrl = momoPaymentService.createPaymentRequest(orderId, orderInfo, amount, returnUrl, notifyUrl);

            if (paymentUrl != null) {
                return ResponseEntity.ok("Vui lòng thanh toán qua đường link sau: " + paymentUrl);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra trong quá trình tạo liên kết thanh toán!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Hệ thống gặp lỗi, vui lòng thử lại sau!");
        }
    }
}

