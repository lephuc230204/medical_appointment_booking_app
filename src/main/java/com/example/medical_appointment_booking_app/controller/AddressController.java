package com.example.medical_appointment_booking_app.controller;

import com.example.medical_appointment_booking_app.payload.request.Dto.AddressDto;
import com.example.medical_appointment_booking_app.payload.request.Form.AddressForm;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import com.example.medical_appointment_booking_app.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @Operation( summary = "Tạo 1 địa chỉ mới", description = "API cho phép người dùng tạo 1 địa chỉ mới")
    @PostMapping("")
    public ResponseEntity<ResponseData<AddressDto>> createAddress(@RequestBody AddressForm form) {
        return ResponseEntity.ok(addressService.createAddress(form));
    }

    @Operation( summary = "Lấy danh sách tỉnh", description = "API cho phép người dùng lấy danh sách tỉnh")
    @GetMapping("/provinces")
    public ResponseEntity<List<Map<String, Object>>> getProvinces() {
        return ResponseEntity.ok(addressService.getProvinces());
    }

    @Operation( summary = "Lấy danh sách huyện theo id tỉnh", description = "API cho phép người dùng Lấy danh sách huyện theo id tỉnh")
    @GetMapping("/districts")
    public ResponseEntity<List<Map<String, Object>>> getDistricts(@RequestParam("provinceId") int provinceId) {
        return ResponseEntity.ok(addressService.getDistricts(provinceId));
    }

    @Operation( summary = "Lấy danh sách xã theo id huyện", description = "API cho phép người dùng Lấy danh sách xã theo id huyện")
    @GetMapping("/wards")
    public ResponseEntity<List<Map<String, Object>>> getWards(@RequestParam("districtId") int districtId) {
        return ResponseEntity.ok(addressService.getWards(districtId));
    }
}
