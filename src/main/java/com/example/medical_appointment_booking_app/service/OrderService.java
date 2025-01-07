package com.example.medical_appointment_booking_app.service;

import com.example.medical_appointment_booking_app.payload.request.Dto.OrderDto;
import com.example.medical_appointment_booking_app.payload.request.Form.OrderForm;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import org.springframework.data.domain.Page;


import java.security.Principal;
import java.util.List;

public interface OrderService {
    ResponseData<OrderDto> create (OrderForm form);

    ResponseData<Page<OrderDto>> getOrders(int page, int size);

    ResponseData<List<OrderDto>> getMyOrder(Principal principal);

    ResponseData<OrderDto> getById(Long orderId);

    ResponseData<OrderDto> changeStatus(Long OrderId, String status );

    ResponseData<OrderDto> cancelOrder(Long orderId);
}
