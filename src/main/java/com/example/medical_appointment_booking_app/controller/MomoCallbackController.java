package com.example.medical_appointment_booking_app.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@Slf4j
@RestController
@RequestMapping("api/v1")
public class MomoCallbackController {

    @PostMapping("/notify")
    public ResponseEntity<String> handleNotify(@RequestBody Map<String, Object> response) {
        log.info("Received MoMo callback: {}", response);

        String orderId = (String) response.get("orderId");
        String resultCode = (String) response.get("resultCode");
        String message = (String) response.get("message");

        // Kiểm tra trạng thái giao dịch
        if ("0".equals(resultCode)) {
            log.info("Payment successful for Order ID: {}", orderId);
            // Cập nhật trạng thái đơn hàng là đã thanh toán
            // orderService.updateOrderStatus(orderId, "Đã thanh toán");
            return ResponseEntity.ok("Payment successful");
        } else {
            log.error("Payment failed for Order ID: {} - {}", orderId, message);
            // Cập nhật trạng thái đơn hàng là thất bại
            // orderService.updateOrderStatus(orderId, "Thanh toán thất bại");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment failed");
        }
    }

    @GetMapping("/return")
    public String handleReturn() {
        return "Payment thanh cong";
    }
}
