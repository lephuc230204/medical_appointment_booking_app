package com.example.medical_appointment_booking_app.service.impl;

import com.example.medical_appointment_booking_app.entity.CartItem;
import com.example.medical_appointment_booking_app.entity.Order;
import com.example.medical_appointment_booking_app.entity.OrderItem;
import com.example.medical_appointment_booking_app.entity.Product;
import com.example.medical_appointment_booking_app.payload.request.Dto.OrderItemDto;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import com.example.medical_appointment_booking_app.repository.OrderItemRepository;
import com.example.medical_appointment_booking_app.repository.OrderRepository;
import com.example.medical_appointment_booking_app.repository.ProductRepository;
import com.example.medical_appointment_booking_app.service.OrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@Slf4j
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<OrderItem> create(List<CartItem> selectedCartItems, Order order) {
        return selectedCartItems.stream()
                .map(cartItem -> {

                    OrderItem orderItem = new OrderItem();
                    orderItem.setProduct(cartItem.getProduct());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setPrice(cartItem.getPrice().doubleValue());
                    orderItem.setOrder(order);

                    // Cập nhật số lượng sản phẩm
                    Product product = cartItem.getProduct();
                    int updatedQuantity = product.getCurrentQuantity() - cartItem.getQuantity();
                    if (updatedQuantity < 0) {
                        throw new RuntimeException("Insufficient stock for product: " + product.getProductName());
                    }
                    product.setCurrentQuantity(updatedQuantity);

                    productRepository.save(product);

                    return orderItem;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ResponseData<List<OrderItemDto>> getOrderItems() {
        log.info("Fetching all order items");

        List<OrderItem> orderItems = orderItemRepository.findAll();
        List<OrderItemDto> orderItemDtos = orderItems.stream()
                .map(OrderItemDto::toDto)
                .collect(Collectors.toList());

        return new ResponseData<>(200, "Fetched all order items successfully", orderItemDtos);
    }

    @Override
    public ResponseData<List<OrderItemDto>> getOrderItemsByOrderId(Long orderId) {
        log.info("Fetching order items by orderId: {}", orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        List<OrderItem> orderItems = orderItemRepository.findByOrder_OrderId(orderId);

        List<OrderItemDto> orderItemDtos = orderItems.stream()
                .map(OrderItemDto::toDto)
                .collect(Collectors.toList());

        return new ResponseData<>(200, "Fetched order items for orderId " + orderId + " successfully", orderItemDtos);
    }


}
