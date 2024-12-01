package com.example.medical_appointment_booking_app.repository;

import com.example.medical_appointment_booking_app.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public  interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

}
