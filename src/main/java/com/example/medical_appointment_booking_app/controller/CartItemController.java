package com.example.medical_appointment_booking_app.controller;


import com.example.medical_appointment_booking_app.payload.request.Dto.CartItemDto;
import com.example.medical_appointment_booking_app.payload.request.Form.CartItemForm;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import com.example.medical_appointment_booking_app.service.CartItemService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("api/v1/cart-items")
@RestController
public class CartItemController {
    private final CartItemService cartItemService;

    @Operation( summary = "Thêm sản phẩm vào giỏ hàng", description = "API cho phép người dùng thêm 1 sản phẩm vào giỏ hàng")
    @PostMapping("/add/{productId}")
    public ResponseEntity<ResponseData<CartItemDto>> addCartItem(@Valid @PathVariable Long productId, @RequestBody CartItemForm cartItemForm){
        return ResponseEntity.ok(cartItemService.addCartItem(productId, cartItemForm));
    }

    @Operation( summary = "Giảm số lượng của 1 sản phẩm trong giỏ hàng", description = "API cho phép người dùng Giảm số lượng của 1 sản phẩm trong giỏ hàng")
    @PostMapping("/remove/{productId}")
    public ResponseEntity<ResponseData<CartItemDto>> removeCartItem(@Valid @PathVariable Long productId, @RequestBody CartItemForm cartItemForm){
        return ResponseEntity.ok(cartItemService.removeCartItem(productId, cartItemForm));
    }

    @Operation( summary = "Xoá sản phẩm khỏi giỏ hàng", description = "API cho phép người dùng Xoá sản phẩm khỏi giỏ hàng")
    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ResponseData<String>> deleteCartItem(@PathVariable Long cartItemId){
        return ResponseEntity.ok(cartItemService.deleteCartItem(cartItemId));
    }
    @Operation( summary = "Xoá tất cả sản phẩm trong giỏ hàng", description = "API cho phép người dùng Xoá tất cả sản phẩm trong giỏ hàng")
    @DeleteMapping
    public ResponseEntity<ResponseData<String>> deleteAllCartItems(){
        return ResponseEntity.ok(cartItemService.deleteAllCartItems());
    }

    @Operation( summary = "Xem chi tiết giỏ hàng", description = "API cho phép người Xem chi tiết giỏ hàng")
    @GetMapping
    public ResponseEntity<ResponseData<Page<CartItemDto>>> getCartItems( @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(cartItemService.getCartItems(page, size));
    }
}
