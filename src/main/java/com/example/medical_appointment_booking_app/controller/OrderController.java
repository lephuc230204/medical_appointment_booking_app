package com.example.medical_appointment_booking_app.controller;


import com.example.medical_appointment_booking_app.payload.request.Dto.OrderDto;
import com.example.medical_appointment_booking_app.payload.request.Form.OrderForm;
import com.example.medical_appointment_booking_app.payload.request.Form.ProductForm;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import com.example.medical_appointment_booking_app.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
// đang sửa
//    @GetMapping("")
//    public ResponseEntity<ResponseData<List<OrderDto>>> getAll(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//        return ResponseEntity.ok(orderService.getOrders(page, size));
//    }

    @GetMapping("me")
    public ResponseEntity<ResponseData<List<OrderDto>>> getMyOrders(Principal principal) {
        return ResponseEntity.ok(orderService.getMyOrder(principal));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ResponseData<OrderDto>> getById( @PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getById(orderId));
    }

    @PutMapping("/change-status/{orderId}")
    public ResponseEntity<ResponseData<OrderDto>> changeStatus( @PathVariable Long orderId, @RequestParam String status){
        return ResponseEntity.ok(orderService.changeStatus(orderId, status));
    }
}
