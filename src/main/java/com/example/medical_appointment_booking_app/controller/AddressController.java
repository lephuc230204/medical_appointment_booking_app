package com.example.medical_appointment_booking_app.controller;

import com.example.medical_appointment_booking_app.service.AddressService;
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

    // Endpoint lấy danh sách tỉnh/thành phố
    @GetMapping("/provinces")
    public ResponseEntity<List<Map<String, Object>>> getProvinces() {
        return ResponseEntity.ok(addressService.getProvinces());
    }

    // Endpoint lấy danh sách quận/huyện theo province_id
    @GetMapping("/districts")
    public ResponseEntity<List<Map<String, Object>>> getDistricts(@RequestParam("provinceId") int provinceId) {
        return ResponseEntity.ok(addressService.getDistricts(provinceId));
    }

    @GetMapping("/wards")
    public ResponseEntity<List<Map<String, Object>>> getWards(@RequestParam("districtId") int districtId) {
        return ResponseEntity.ok(addressService.getWards(districtId));
    }
}
