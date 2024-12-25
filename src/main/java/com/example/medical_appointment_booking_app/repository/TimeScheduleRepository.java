package com.example.medical_appointment_booking_app.repository;

import com.example.medical_appointment_booking_app.entity.TimeSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimeScheduleRepository extends JpaRepository<TimeSchedule, Long> {
    boolean existsByAppointmentDate(LocalDate appointmentDate);
    Optional<TimeSchedule> findByAppointmentDate(LocalDate appointmentDate);
    List<TimeSchedule> findAllByAppointmentDate(LocalDate appointmentDate);
}
