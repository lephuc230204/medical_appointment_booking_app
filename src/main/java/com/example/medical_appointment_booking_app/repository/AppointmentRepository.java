package com.example.medical_appointment_booking_app.repository;

import com.example.medical_appointment_booking_app.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
}
