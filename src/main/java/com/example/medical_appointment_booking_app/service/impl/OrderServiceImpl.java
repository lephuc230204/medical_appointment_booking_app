package com.example.medical_appointment_booking_app.service.impl;

import com.example.medical_appointment_booking_app.entity.*;
import com.example.medical_appointment_booking_app.payload.request.Dto.OrderDto;
import com.example.medical_appointment_booking_app.payload.request.Dto.ProductDto;
import com.example.medical_appointment_booking_app.payload.request.Form.OrderForm;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import com.example.medical_appointment_booking_app.payload.response.ResponseError;
import com.example.medical_appointment_booking_app.repository.*;
import com.example.medical_appointment_booking_app.service.OrderItemService;
import com.example.medical_appointment_booking_app.service.OrderService;
import com.example.medical_appointment_booking_app.service.ShipFeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final ShipFeeService shipFeeService;
    private final OrderItemService orderItemService;
    private final AddressRepository addressRepository;

    @Override
    public ResponseData<OrderDto> create( OrderForm form, List<Long> cartItemIds) {
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        log.info("Creating order for user {}", user);

        Cart cart = cartRepository.findByUser_UserId(user.getUserId());
        if (cart == null) {
            throw new RuntimeException("No cart found for user");
        }

        if (cartItemIds == null || cartItemIds.isEmpty()) {
            log.error("Cart items are empty");
            return new ResponseError<>(400, "Cart items cannot be empty");
        }

        List<CartItem> cartItems = cartItemRepository.findAllByCartItemIdIn(cartItemIds);

        if (cartItems.isEmpty()) {
            log.warn("No items selected for checkout by user {}", user.getUsername());
            throw new RuntimeException("No items selected for checkout. Please select items and try again.");
        }

        Address address = addressRepository.findById(form.getAddressId()).orElse(null);
        if (address == null) {
            log.error("Address not found");
            return new ResponseError<>(400, "Address not found");
        }


        Order order = new Order();
        order.setUser(user);
        order.setPaymentMethod(form.getPaymentMethod());
        order.setStatus(Order.Status.PENDING);
        order.setPhone(form.getPhone());
        order.setShippingUnit(form.getShippingUnit());
        order.setAddress(address);
        order.setNote(form.getNote());
        order.setOrderDate(LocalDate.now());
        Double totalPrice = cartItems.stream()
                .mapToDouble(cartItem -> cartItem.getQuantity() * cartItem.getPrice())
                .sum();
        List<OrderItem> orderItems = orderItemService.create(cartItems, order);

        double shippingFee = shipFeeService.calculateShipFee(orderItems, address);

        order.setTotalPrice(totalPrice);
        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);

        order.setShippingFee(shippingFee);
        order.setOrderItems(orderItems);
        orderRepository.save(order);
        return new ResponseData<>(200, "Create new product successfully", OrderDto.fromEntity(order));
    }

    @Override
    public ResponseData<List<OrderDto>> getOrders(int page, int size) {
        log.info("Get Order list");
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orders = orderRepository.findAll(pageable);
        List<OrderDto> data = orders.getContent().stream()
                .map(OrderDto::fromEntity)
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
                .map(OrderDto::fromEntity)
                .collect(Collectors.toList());

        return new ResponseData<>(200, "Get orders successfully", data);
    }

    @Override
    public ResponseData<OrderDto> getById(Long orderId) {
        log.info("Get Order by ID");
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        return new ResponseData<> (200, "Get order successfully", OrderDto.fromEntity(order) );
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
        return new ResponseData<>(200, "Order status updated successfully",OrderDto.fromEntity(order));
    }
}
