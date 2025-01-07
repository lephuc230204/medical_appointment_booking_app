package com.example.medical_appointment_booking_app.controller.admin;

import com.example.medical_appointment_booking_app.payload.request.Dto.CartDto;
import com.example.medical_appointment_booking_app.payload.request.Dto.OrderDto;
import com.example.medical_appointment_booking_app.payload.request.Dto.ProductDto;
import com.example.medical_appointment_booking_app.payload.request.Dto.UserDto;
import com.example.medical_appointment_booking_app.payload.request.Form.ProductForm;
import com.example.medical_appointment_booking_app.payload.request.Form.TimeScheduleForm;
import com.example.medical_appointment_booking_app.payload.request.Form.UserForm;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import com.example.medical_appointment_booking_app.service.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name = "Admin Controller", description = "API quản lý dành cho admin")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/admin")
public class AdminController {
    private final UserService userService;
    private final TimeScheduleService timeScheduleService;
    private final ScheduleService scheduleService;
    private final OrderService orderService;
    private final ProductService productService;
    private final CartService cartService;

    @Operation(summary = "Lấy danh sách người dùng", description = "API trả về danh sách người dùng với phân trang. Mặc định là trang đầu tiên với 10 người dùng.")
    @GetMapping("/user")
    public ResponseEntity<Page<UserDto>> getUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(userService.getUsers(page, size));
    }

    @Operation(summary = "Tạo mới 1 người dùng", description = "API cho admin tạo mới 1 người dùng")
    @PostMapping("")
    public ResponseEntity<UserDto> createUser(@RequestBody UserForm form){
        return ResponseEntity.ok(userService.createUser(form));
    }

    @Operation(summary = "Tạo thời gian khám bệnh", description = "API cho phép admin tạo thời gian để bác sĩ khám bệnh dựa trên thông tin từ form nhập vào.")
    @PostMapping("/time-schedule")
    public ResponseEntity<ResponseData<String>> createTimeSchedule(@RequestBody TimeScheduleForm form) {
        return ResponseEntity.ok(timeScheduleService.createTimeSchedule(form));
    }

    @Operation(summary = "Thêm sản phẩm mới vào Store", description = "API cho phép admin thêm một sản phẩm mới vào Store. Dữ liệu sản phẩm được gửi dưới dạng form-data.")
    @PostMapping("/products")
    public ResponseEntity<ResponseData<?>> create(@ModelAttribute @Valid ProductForm form) throws IOException {
        return ResponseEntity.ok(productService.create(form));
    }

    @Operation(summary = "Chỉnh sửa sản phẩm", description = "API cho phép admin chỉnh sửa sản phẩm nếu thêm sai thông tin. Yêu cầu gửi lại thông tin sản phẩm mới.")
    @PutMapping("/products/{productId}")
    public ResponseEntity<ResponseData<ProductDto>> update(@PathVariable Long productId, @ModelAttribute @Valid ProductForm form) throws IOException {
        return ResponseEntity.ok(productService.updateProduct(productId, form));
    }

    @Operation(summary = "Xem chi tiết sản phẩm", description = "API cho phép admin xem thông tin chi tiết của một sản phẩm dựa trên ID của sản phẩm.")
    @GetMapping("/products/{productId}")
    public ResponseEntity<ResponseData<ProductDto>> getProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @Operation( summary = "Xem danh sách sản phẩm", description = "API cho phép admin lay danh sách tất cả sản phẩm")
    @GetMapping("/orders")
    public ResponseEntity<ResponseData<Page<OrderDto>>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(orderService.getOrders(page, size));
    }

    @Operation( summary = "thay đổi trạng thái đơn hàng", description = "API cho phép admin thay đổi trạng thái đơn hàng")
    @PutMapping("/orders/change-status/{orderId}")
    public ResponseEntity<ResponseData<OrderDto>> changeStatus( @PathVariable Long orderId, @RequestParam String status){
        return ResponseEntity.ok(orderService.changeStatus(orderId, status));
    }
// đang sửa
//    @Operation( summary = "Tìm giỏ hàng của người dùng theo ID", description = "API cho phép admin Tìm giỏ hàng của người dùng theo ID")
//    @GetMapping("/cart")
//    public ResponseEntity<ResponseData<CartDto>> getCartByUserId(){
//        return ResponseEntity.ok(cartService.getCartByUserId());
//    }

}
