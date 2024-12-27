package com.example.medical_appointment_booking_app.service;

import com.example.medical_appointment_booking_app.payload.request.Dto.AddressDto;
import com.example.medical_appointment_booking_app.payload.request.Form.AddressForm;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;

import java.util.List;
import java.util.Map;

public interface AddressService {
    ResponseData<AddressDto> createAddress(AddressForm form);

    List<Map<String, Object>> getProvinces();

    List<Map<String, Object>> getDistricts(int provinceId);

    List<Map<String, Object>> getWards(int districtId);
}
