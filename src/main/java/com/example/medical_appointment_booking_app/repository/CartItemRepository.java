package com.example.medical_appointment_booking_app.repository;

import com.example.medical_appointment_booking_app.entity.Cart;
import com.example.medical_appointment_booking_app.entity.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Page<CartItem> findByCart(Cart cart, Pageable pageable);

    Optional<CartItem> findByCartAndProduct_ProductId(Cart cart, Long productId);
    List<CartItem> findAllByCartItemIdIn(List<Long> cartItemIds);
}
