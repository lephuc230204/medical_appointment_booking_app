package com.example.medical_appointment_booking_app.repository;

import com.example.medical_appointment_booking_app.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByDistrictIdAndProvinceIdAndWardIdAndFullAddress(
            int districtId, int provinceId, String wardId, String fullAddress);
}
