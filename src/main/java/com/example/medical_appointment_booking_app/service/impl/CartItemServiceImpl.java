package com.example.medical_appointment_booking_app.service.impl;

import com.example.medical_appointment_booking_app.entity.Cart;
import com.example.medical_appointment_booking_app.entity.CartItem;
import com.example.medical_appointment_booking_app.entity.Product;
import com.example.medical_appointment_booking_app.entity.User;
import com.example.medical_appointment_booking_app.payload.request.Dto.CartItemDto;
import com.example.medical_appointment_booking_app.payload.request.Form.CartItemForm;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import com.example.medical_appointment_booking_app.payload.response.ResponseError;
import com.example.medical_appointment_booking_app.repository.CartItemRepository;
import com.example.medical_appointment_booking_app.repository.CartRepository;
import com.example.medical_appointment_booking_app.repository.ProductRepository;
import com.example.medical_appointment_booking_app.repository.UserRepository;
import com.example.medical_appointment_booking_app.service.CartItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    @Override
    @Transactional
    public ResponseData<String> addCartItem(Long productId, CartItemForm cartItemForm) {
        // Lấy thông tin user từ context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            log.error("User not found for email {}", email);
            return new ResponseError<>(404, "User not found");
        }

        // Lấy giỏ hàng của user
        Cart cart = cartRepository.findByUser_UserId(user.getUserId());

        // Lấy thông tin sản phẩm
        Product product = productRepository.findByProductId(productId)
                .orElse(null);
        if (product == null) {
            log.error("Product not found for id {}", productId);
            return new ResponseError<>(404, "Product not found");
        }

        // Kiểm tra nếu số lượng yêu cầu lớn hơn số lượng hiện tại
        if (cartItemForm.getQuantity() > product.getCurrentQuantity()) {
            log.error("Requested quantity is greater than available quantity");
            return new ResponseError<>(400, "Requested quantity is greater than available quantity");
        }

        // Kiểm tra xem sản phẩm đã có trong giỏ hàng hay chưa
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(product.getProductId()))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem itemToUpdate = existingItem.get();

            // Cập nhật số lượng mới
            int newQuantity = itemToUpdate.getQuantity() + cartItemForm.getQuantity();

            // Kiểm tra nếu số lượng mới vượt quá số lượng hiện tại
            if (newQuantity > product.getCurrentQuantity()) {
                log.error("Requested quantity is greater than available quantity");
                return new ResponseError<>(400, "Requested quantity is greater than available quantity");
            }

            itemToUpdate.setQuantity(newQuantity);
            itemToUpdate.setPrice(product.getPrice().multiply(BigDecimal.valueOf(newQuantity)));

            cartItemRepository.save(itemToUpdate);

            return new ResponseData<>(200, "Cart item updated successfully");
        } else {
            // Tạo sản phẩm mới trong giỏ hàng
            CartItem newItem = CartItem.builder()
                    .product(product)
                    .quantity(cartItemForm.getQuantity())
                    .price(product.getPrice().multiply(BigDecimal.valueOf(cartItemForm.getQuantity())))
                    .cart(cart)
                    .build();

            cartItemRepository.save(newItem);

            return new ResponseData<>(200, "Cart item added successfully");
        }
    }



    @Override
    public ResponseData<String> removeCartItem(Long productId, CartItemForm cartItemForm) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            log.error("User not found for email {}", email);
            return new ResponseError<>(404, "User not found");
        }

        Cart cart = cartRepository.findByUser_UserId(user.getUserId());
        if (cart == null) {
            log.error("Cart not found for user id {}", user.getUserId());
            return new ResponseError<>(404, "Cart not found");
        }

        CartItem cartItem = cartItemRepository.findByCartAndProduct_ProductId(cart, productId).orElse(null);
        if (cartItem == null) {
            log.error("Item not found in cart for product id {}", productId);
            return new ResponseError<>(404, "Item not found in cart");
        }

        if (cartItem.getQuantity() < cartItemForm.getQuantity()) {
            log.error("Attempt to remove more items than present in the cart");
            return new ResponseError<>(400, "Cannot remove more items than are in the cart");
        }

        int newQuantity = cartItem.getQuantity() - cartItemForm.getQuantity();
        if (newQuantity > 0) {
            cartItem.setQuantity(newQuantity);
            cartItem.setPrice(cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(newQuantity)));
            cartItemRepository.save(cartItem);
        } else {
            cartItemRepository.delete(cartItem);
        }

        return new ResponseData<>(200, "Cart item removed successfully");
    }



    @Override
    public ResponseData<String> deleteCartItem(Long cartItemId) {
        // Lấy thông tin người dùng đang đăng nhập
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Tìm người dùng theo email
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            log.error("User not found for email {}", email);
            return new ResponseError<>(404, "User not found");
        }

        // Lấy giỏ hàng của người dùng
        Cart cart = cartRepository.findByUser_UserId(user.getUserId());
        if (cart == null) {
            log.error("Cart not found for user id {}", user.getUserId());
            return new ResponseError<>(404, "Cart not found");
        }

        // Tìm CartItem theo ID
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElse(null);
        if (cartItem == null) {
            log.error("Item not found for id {}", cartItemId);
            return new ResponseError<>(404, "Item not found");
        }

        if (!cartItem.getCart().getCartId().equals(cart.getCartId())) {
            log.error("Unauthorized attempt to delete cart item {} by user {}", cartItemId, email);
            return new ResponseError<>(403, "You are not authorized to delete this item");
        }

        // Xóa CartItem
        cartItemRepository.delete(cartItem);
        log.info("Cart item {} deleted successfully by user {}", cartItemId, email);
        return new ResponseData<>(200, "Cart item deleted successfully");
    }


    @Override
    public ResponseData<Page<CartItemDto>> getCartItems(int page, int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            log.error("User not found for email {}", email);
            return new ResponseError<>(404, "User not found");
        }

        Cart cart = cartRepository.findByUser_UserId(user.getUserId());

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc("cartItemId")));
        Page<CartItem> cartItems = cartItemRepository.findByCart(cart, pageable);
        Page<CartItemDto> cartItemDtos = cartItems.map(CartItemDto::toDto);

        return new ResponseData<>(200, "Cart items retrieved successfully", cartItemDtos);
    }

    @Override
    public ResponseData<String> deleteAllCartItems() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            log.error("User not found for email {}", email);
            return new ResponseError<>(404, "User not found");
        }
        Cart cart = cartRepository.findByUser_UserId(user.getUserId());
        if (cart.getItems().isEmpty()) {
            log.info("Cart is already empty for user with email {}", email);
            return new ResponseData<>(200, "Cart is already empty");
        }
        cartItemRepository.deleteAll(cart.getItems());
        log.info("All cart items deleted for user with email {}", email);

        return new ResponseData<>(200, "Cart items deleted successfully");
    }

}
