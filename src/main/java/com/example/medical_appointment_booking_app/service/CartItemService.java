package com.example.medical_appointment_booking_app.service;

import com.example.medical_appointment_booking_app.payload.request.Dto.CartItemDto;
import com.example.medical_appointment_booking_app.payload.request.Form.CartItemForm;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CartItemService {
    ResponseData<CartItemDto> addCartItem(Long productId, CartItemForm cartItemForm);

    ResponseData<CartItemDto> removeCartItem(Long productId, CartItemForm cartItemForm);

    ResponseData<String> deleteCartItem(Long cartItemId);

    ResponseData<Page<CartItemDto>> getCartItems(int page, int size);

    ResponseData<String> deleteAllCartItems();
}
