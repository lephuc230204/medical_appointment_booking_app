package com.example.medical_appointment_booking_app.repository;

import com.example.medical_appointment_booking_app.entity.Appointment;
import com.example.medical_appointment_booking_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

    List<Appointment> findAllByUser(User user);

}
