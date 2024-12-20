package com.example.medical_appointment_booking_app.service.impl;

import com.example.medical_appointment_booking_app.repository.AddressRepository;
import com.example.medical_appointment_booking_app.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<Map<String,Object>> getProvinces(){
        String url = "https://online-gateway.ghn.vn/shiip/public-api/master-data/province";
        HttpHeaders headers = new HttpHeaders();
        headers.set("token" , ghnToken);
        headers.set("Content-Type", "application/json");

        HttpEntity<Void> request = new HttpEntity<> (headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);

        Map<String,Object> data = response.getBody();
        List<Map<String,Object>> allProvinces = (List<Map<String,Object>>) data.get("data");

        // Filter data tu api tra ve
        List<Map<String, Object>> filteredProvinces = new ArrayList<>();

        for (Map<String, Object> province : allProvinces) {
            Map<String, Object> filteredProvince = new HashMap<>();
            filteredProvince.put("ProvinceID", province.get("ProvinceID"));
            filteredProvince.put("ProvinceName", province.get("ProvinceName"));
            filteredProvinces.add(filteredProvince);
        }
        return filteredProvinces;
    }

    public List<Map<String, Object>> getDistricts(int provinceId){
        String url = "https://online-gateway.ghn.vn/shiip/public-api/master-data/district";

        HttpHeaders headers = new HttpHeaders();
        headers.set("token" , ghnToken);
        headers.set("Content-Type", "application/json");

        Map<String,Integer> payload = new HashMap<>();
        payload.put("provinceId", provinceId);

        HttpEntity<Map<String, Integer>> request = new HttpEntity<> (payload, headers);

        ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST,  request, Map.class);

        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();

        List<Map<String,Object>> districts = (List<Map<String,Object>>) responseBody.get("data");

        List<Map<String, Object>> filteredDistricts = new ArrayList<>();
        for (Map<String, Object> district : districts) {
            Map<String, Object> filteredDistrict = new HashMap<>();
            filteredDistrict.put("DistrictID", district.get("DistrictID"));
            filteredDistrict.put("ProvinceID", district.get("ProvinceID"));
            filteredDistrict.put("DistrictName", district.get("DistrictName"));

            filteredDistricts.add(filteredDistrict);
        }
        return filteredDistricts;

    }

}
