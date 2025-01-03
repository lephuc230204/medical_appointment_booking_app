package com.example.medical_appointment_booking_app.service.impl;

import com.example.medical_appointment_booking_app.entity.*;
import com.example.medical_appointment_booking_app.payload.request.Dto.OrderDto;
import com.example.medical_appointment_booking_app.payload.request.Dto.ProductDto;
import com.example.medical_appointment_booking_app.payload.request.Form.CartItemRequest;
import com.example.medical_appointment_booking_app.payload.request.Form.OrderForm;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import com.example.medical_appointment_booking_app.payload.response.ResponseError;
import com.example.medical_appointment_booking_app.repository.*;
import com.example.medical_appointment_booking_app.service.CartItemService;
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
    private final CartItemService cartItemService;

    @Override
    public ResponseData<OrderDto> create(OrderForm form) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        log.info("Creating order for user {}", user);

        // Lấy giỏ hàng của người dùng
        Cart cart = cartRepository.findByUser_UserId(user.getUserId());
        if (cart == null) {
            throw new RuntimeException("No cart found for user");
        }

        // Kiểm tra nếu giỏ hàng không có sản phẩm
        if (form.getCartItems() == null || form.getCartItems().isEmpty()) {
            log.error("Cart items are empty");
            return new ResponseError<>(400, "Cart items cannot be empty");
        }

        // Lấy danh sách CartItemID từ CartItemRequest
        List<Long> cartItemIds = form.getCartItems().stream()
                .map(CartItemRequest::getCartItemId)
                .collect(Collectors.toList());

        // Lấy tất cả CartItem từ cơ sở dữ liệu
        List<CartItem> cartItems = cartItemRepository.findAllByCartItemIdIn(cartItemIds);
        if (cartItems.isEmpty()) {
            log.warn("No items selected for checkout by user {}", user.getUsername());
            throw new RuntimeException("No items selected for checkout. Please select items and try again.");
        }

        // Cập nhật số lượng cho các CartItem và tạo OrderItem
        List<OrderItem> orderItems = form.getCartItems().stream()
                .map(cartItemRequest -> {
                    // Tìm CartItem theo ID
                    CartItem cartItem = cartItems.stream()
                            .filter(item -> item.getCartItemId().equals(cartItemRequest.getCartItemId()))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Cart item not found"));

                    // Kiểm tra số lượng sản phẩm trong kho
                    int remainingQuantity = cartItem.getQuantity() - cartItemRequest.getQuantity();
                    if (remainingQuantity < 0) {
                        throw new RuntimeException("Not enough stock for item: " + cartItem.getProduct().getProductName());
                    }

                    // Cập nhật số lượng mới cho CartItem
                    cartItem.setQuantity(remainingQuantity);
                    cartItemRepository.save(cartItem);  // Lưu lại CartItem đã cập nhật

                    // Tạo OrderItem từ CartItem
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProduct(cartItem.getProduct());
                    orderItem.setQuantity(cartItemRequest.getQuantity());
                    orderItem.setPrice(cartItem.getPrice());
                    return orderItem;
                })
                .collect(Collectors.toList());

        // Kiểm tra địa chỉ giao hàng
        Address address = addressRepository.findById(form.getAddressId()).orElse(null);
        if (address == null) {
            log.error("Address not found");
            return new ResponseError<>(400, "Address not found");
        }

        // Tạo đối tượng Order
        Order order = new Order();
        order.setUser(user);
        order.setPaymentMethod(form.getPaymentMethod());
        order.setStatus(Order.Status.PENDING);
        order.setPhone(form.getPhone());
        order.setShippingUnit(form.getShippingUnit());
        order.setAddress(address);
        order.setNote(form.getNote());
        order.setOrderDate(LocalDate.now());

        // Tính tổng giá trị đơn hàng
        Double totalPrice = orderItems.stream()
                .mapToDouble(orderItem -> orderItem.getQuantity() * orderItem.getPrice())
                .sum();

        // Tính phí vận chuyển
        double shippingFee = shipFeeService.calculateShipFee(orderItems, address);

        // Cập nhật thông tin đơn hàng
        order.setTotalPrice(totalPrice);
        order.setShippingFee(shippingFee);
        order.setOrderItems(orderItems);

        // Lưu đơn hàng và OrderItem vào cơ sở dữ liệu
        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);

        return new ResponseData<>(200, "Create order successfully", OrderDto.fromEntity(order));
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
