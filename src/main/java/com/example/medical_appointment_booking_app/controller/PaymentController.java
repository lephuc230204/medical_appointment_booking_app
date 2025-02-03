package com.example.medical_appointment_booking_app.controller;


import com.example.medical_appointment_booking_app.payload.request.Form.MomoRequest;
import com.example.medical_appointment_booking_app.service.MomoPaymentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/momo")
@RestController
public class PaymentController {
    @Autowired
    private MomoPaymentService momoService;

    @Operation(summary = "Thanh toán MOMO", description = "Nhận so tien va thuc hien goi api toi momo de lay ve link QR thanh toan")
    @PostMapping()
    public ResponseEntity<String> testPayment(@RequestBody MomoRequest paymentRequest) {
        return ResponseEntity.ok(momoService.createPaymentRequest(paymentRequest.getAmount()));

    }

    @GetMapping("/order-status/{orderId}")
    public String checkPaymentStatus(@PathVariable String orderId) {
        String response = momoService.checkPaymentStatus(orderId);
        return response;
    }
}
