package com.example.medical_appointment_booking_app.controller;

import com.example.medical_appointment_booking_app.service.MomoPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private MomoPaymentService momoPaymentService;

    @GetMapping("/create-payment")
    public ResponseEntity<String> createPayment(@RequestParam(required = true) Long orderId,
                                                @RequestParam(required = true) String orderInfo,
                                                @RequestParam(required = true) long amount) {
        logger.info("Received request to create payment for Order ID: {}", orderId);

        // Kiểm tra các tham số đầu vào
        if (orderId == null || orderInfo == null || orderInfo.isBlank() || amount <= 0) {
            logger.warn("Invalid input parameters: orderId={}, orderInfo={}, amount={}", orderId, orderInfo, amount);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tham số đầu vào không hợp lệ!");
        }

        try {
            String returnUrl = "http://localhost:8080/return";
            String notifyUrl = "http://localhost:8080/notify";

            // Gọi service để tạo URL thanh toán
            String paymentUrl = momoPaymentService.createPaymentRequest(orderId, orderInfo, amount, returnUrl, notifyUrl);

            if (paymentUrl != null) {
                logger.info("Payment URL successfully created for Order ID: {}", orderId);
                return ResponseEntity.ok("Vui lòng thanh toán qua đường link sau: " + paymentUrl);
            } else {
                logger.error("Failed to create payment URL for Order ID: {}", orderId);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra trong quá trình tạo liên kết thanh toán!");
            }
        } catch (Exception e) {
            logger.error("Unexpected error occurred while creating payment for Order ID: {}", orderId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Hệ thống gặp lỗi, vui lòng thử lại sau!");
        }
    }
}

