package com.example.medical_appointment_booking_app.payload.request.Form;

import com.example.medical_appointment_booking_app.entity.Order;
import lombok.Data;

import java.util.List;

@Data
public class OrderForm {
    private Long addressId;
    private Order.Payment paymentMethod;
    private String phone;
    private String voucher;
    private List<Long> cartItemIds;
    private String shippingUnit;
    private String note;
}
