package com.example.medical_appointment_booking_app.repository;

import com.example.medical_appointment_booking_app.entity.DailyAppointmentStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyAppointmentStatsRepository extends JpaRepository<DailyAppointmentStats, Long> {
    Optional<DailyAppointmentStats> findByDate(LocalDate date);
}
