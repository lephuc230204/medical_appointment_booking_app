package com.example.medical_appointment_booking_app.service.impl;

import com.example.medical_appointment_booking_app.entity.*;
import com.example.medical_appointment_booking_app.payload.request.Dto.OrderDto;
import com.example.medical_appointment_booking_app.payload.request.Dto.ProductDto;
import com.example.medical_appointment_booking_app.payload.request.Form.OrderForm;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import com.example.medical_appointment_booking_app.repository.*;
import com.example.medical_appointment_booking_app.service.OrderItemService;
import com.example.medical_appointment_booking_app.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private OrderItemService orderItemService;

    @Override
    public ResponseData<OrderDto> create(Principal principal, OrderForm form) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        log.info("Creating order for user {}", user);

        Cart cart = cartRepository.findByUser_UserId(user.getUserId());
        if (cart == null) {
            throw new RuntimeException("No cart found for user");
        }
        List<CartItem> selectedCartItems = cartItemRepository.findAllById(form.getCartItemId());

        if (selectedCartItems.isEmpty()) {
            log.warn("No items selected for checkout by user {}", user.getUsername());
            throw new RuntimeException("No items selected for checkout. Please select items and try again.");
        }

        Double totalAmount = selectedCartItems.stream()
                .mapToDouble(cartItem -> cartItem.getQuantity() * cartItem.getPrice().doubleValue())
                .sum();

        Order order = new Order();
        order.setUser(user);
        order.setPaymentMethod(form.getPayment());
        order.setStatus(Order.Status.PENDING);
        order.setPhoneNumber(form.getPhoneNumber());
        order.setAddress(form.getAddress());
        order.setOrderDate(LocalDate.now());
        order.setTotalAmount(totalAmount);
        orderRepository.save(order);
        log.info("Order created with ID: {}", order.getOrderId());

        List<OrderItem> orderItems = orderItemService.create(selectedCartItems, order);
        orderItemRepository.saveAll(orderItems);
        log.info("Created {} order items for order ID: {}", orderItems.size(), order.getOrderId());

        return new ResponseData<>(200, "Create new product successfully", OrderDto.toDto(order));
    }

    @Override
    public ResponseData<List<OrderDto>> getOrders(int page, int size) {
        log.info("Get Order list");
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orders = orderRepository.findAll(pageable);
        List<OrderDto> data = orders.getContent().stream()
                .map(OrderDto::toDto)
                .collect(Collectors.toList());
        return new ResponseData<>(200, "Get orders successfully", data);
    }

    @Override
    public ResponseData<List<OrderDto>> getMyOrder(Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        log.info("Get My Order list");

        List<Order> orders = orderRepository.findByUser(user);

        List<OrderDto> data = orders.stream()
                .map(OrderDto::toDto)
                .collect(Collectors.toList());

        return new ResponseData<>(200, "Get orders successfully", data);
    }

    @Override
    public ResponseData<OrderDto> getById(Long orderId) {
        log.info("Get Order by ID");
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        return new ResponseData<> (200, "Get order successfully", OrderDto.toDto(order) );
    }

    @Override
    public ResponseData<OrderDto> changeStatus(Long orderId, String status) {
        log.info("Change OrderStatus by ID");
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        try {
            Order.Status newStatus = Order.Status.valueOf(status.toUpperCase());
            order.setStatus(newStatus);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status value: " + status);
        }
        orderRepository.save(order);
        return new ResponseData<>(200, "Order status updated successfully",OrderDto.toDto(order));
    }
}
