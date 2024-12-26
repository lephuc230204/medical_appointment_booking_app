package com.example.medical_appointment_booking_app.service.impl;

import com.example.medical_appointment_booking_app.repository.AddressRepository;
import com.example.medical_appointment_booking_app.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AddressServiceImpl implements AddressService {
    @Value("${ghn.token}")
    private String ghnToken;
    @Value("${ghn.apiUrl.getProvinces}")
    private String getProvincesUrl;
    @Value("${ghn.apiUrl.getDistrict}")
    private String getDistrictsUrl;
    @Value("${ghn.apiUrl.getWard}")
    private String getWardsUrl;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<Map<String, Object>> getProvinces() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("token", ghnToken);
        headers.set("Content-Type", "application/json");

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(getProvincesUrl, HttpMethod.GET, request, Map.class);

        Map<String, Object> data = response.getBody();
        if (data == null || !data.containsKey("data")) {
            throw new RuntimeException("Không nhận được dữ liệu từ GHN API");
        }

        List<Map<String, Object>> allProvinces = (List<Map<String, Object>>) data.get("data");
        List<Map<String, Object>> filteredProvinces = new ArrayList<>();

        for (Map<String, Object> province : allProvinces) {
            Map<String, Object> filteredProvince = new HashMap<>();
            filteredProvince.put("ProvinceID", province.get("ProvinceID"));
            filteredProvince.put("ProvinceName", province.get("ProvinceName"));
            filteredProvinces.add(filteredProvince);
        }

        return filteredProvinces;
    }

    @Override
    public List<Map<String, Object>> getDistricts(int provinceId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("token", ghnToken);
        headers.set("Content-Type", "application/json");

        Map<String, Integer> payload = new HashMap<>();
        payload.put("province_id", provinceId);

        HttpEntity<Map<String, Integer>> request = new HttpEntity<>(payload, headers);
        ResponseEntity<Map> response = restTemplate.exchange(getDistrictsUrl, HttpMethod.POST, request, Map.class);

        Map<String, Object> data = response.getBody();
        if (data == null || !data.containsKey("data")) {
            throw new RuntimeException("Không nhận được dữ liệu từ GHN API");
        }

        List<Map<String, Object>> districts = (List<Map<String, Object>>) data.get("data");
        List<Map<String, Object>> filteredDistricts = new ArrayList<>();

        for (Map<String, Object> district : districts) {
            Map<String, Object> filteredDistrict = new HashMap<>();
            filteredDistrict.put("DistrictID", district.get("DistrictID"));
            filteredDistrict.put("DistrictName", district.get("DistrictName"));
            filteredDistrict.put("ProvinceID", district.get("ProvinceID"));
            filteredDistricts.add(filteredDistrict);
        }

        return filteredDistricts;
    }

    @Override
    public List<Map<String, Object>> getWards(int districtId) {
        String url = getWardsUrl + "?district_id=" + districtId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("token", ghnToken);
        headers.set("Content-Type", "application/json");

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);

        Map<String, Object> data = response.getBody();
        if (data == null || !data.containsKey("data")) {
            throw new RuntimeException("Không nhận được dữ liệu từ GHN API");
        }

        List<Map<String, Object>> wards = (List<Map<String, Object>>) data.get("data");
        List<Map<String, Object>> filteredWards = new ArrayList<>();

        for (Map<String, Object> ward : wards) {
            Map<String, Object> filteredWard = new HashMap<>();
            filteredWard.put("WardCode", ward.get("WardCode"));
            filteredWard.put("WardName", ward.get("WardName"));
            filteredWard.put("DistrictID", ward.get("DistrictID"));
            filteredWards.add(filteredWard);
        }

        return filteredWards;
    }
}
