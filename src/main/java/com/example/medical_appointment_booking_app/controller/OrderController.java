package com.example.medical_appointment_booking_app.controller;


import com.example.medical_appointment_booking_app.payload.request.Dto.OrderDto;
import com.example.medical_appointment_booking_app.payload.request.Form.OrderForm;
import com.example.medical_appointment_booking_app.payload.request.Form.ProductForm;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import com.example.medical_appointment_booking_app.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/order")
@RestController
public class OrderController {
    private final OrderService orderService;

    @Operation( summary = "Tạo 1 đơn hàng", description = "API cho phép người dùng Tạo 1 đơn hàng mới")
    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderForm form) {
        try {
            ResponseData<?> response = orderService.create(form);
            return ResponseEntity.status(response.getStatus()).body(response);
        } catch (Exception e) {
            log.error("Error creating order: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @Operation( summary = "Xem tất cả đơn hàng của bạn", description = "API cho phép người dùng Xem tất cả đơn hàng của họ")
    @GetMapping("me")
    public ResponseEntity<ResponseData<List<OrderDto>>> getMyOrders(Principal principal) {
        return ResponseEntity.ok(orderService.getMyOrder(principal));
    }

    @Operation( summary = "Xem 1 đơn hàng cụ thể ", description = "API cho phép người dùng xem 1 đơn hàng cụ thể")
    @GetMapping("/{orderId}")
    public ResponseEntity<ResponseData<OrderDto>> getById( @PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getById(orderId));
    }

    @Operation( summary = "Cho phép huỷ đơn", description = "API cho phép người dùng huỷ đơn")
    @PutMapping("/cancle/{orderId}")
    public ResponseEntity<ResponseData<OrderDto>> cancelOrder( @PathVariable Long orderId){
        return ResponseEntity.ok(orderService.cancelOrder(orderId));
    }
}
