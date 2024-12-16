package com.example.medical_appointment_booking_app.controller;


import com.example.medical_appointment_booking_app.payload.request.Dto.CartItemDto;
import com.example.medical_appointment_booking_app.payload.request.Form.CartItemForm;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import com.example.medical_appointment_booking_app.service.CartItemService;
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

    @PostMapping("/add/{productId}")
    public ResponseEntity<ResponseData<String>> addCartItem(@Valid @PathVariable Long productId, @RequestBody CartItemForm cartItemForm){
        return ResponseEntity.ok(cartItemService.addCartItem(productId, cartItemForm));
    }

    @PostMapping("/remove/{productId}")
    public ResponseEntity<ResponseData<String>> removeCartItem(@Valid @PathVariable Long productId, @RequestBody CartItemForm cartItemForm){
        return ResponseEntity.ok(cartItemService.removeCartItem(productId, cartItemForm));
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ResponseData<String>> deleteCartItem(@PathVariable Long cartItemId){
        return ResponseEntity.ok(cartItemService.deleteCartItem(cartItemId));
    }
    @DeleteMapping
    public ResponseEntity<ResponseData<String>> deleteAllCartItems(){
        return ResponseEntity.ok(cartItemService.deleteAllCartItems());
    }

    @GetMapping
    public ResponseEntity<ResponseData<Page<CartItemDto>>> getCartItems( @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(cartItemService.getCartItems(page, size));
    }
}
