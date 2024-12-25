package com.example.medical_appointment_booking_app.repository;

import com.example.medical_appointment_booking_app.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
