package com.example.medical_appointment_booking_app.repository;

import com.example.medical_appointment_booking_app.entity.Schedule;
import com.example.medical_appointment_booking_app.entity.TimeSchedule;
import com.example.medical_appointment_booking_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByUser(User user);
    List<Schedule> findByTimeScheduleAndUser(TimeSchedule timeSchedule, User user);
    List<Schedule> findByTimeScheduleInAndUser(List<TimeSchedule> timeSchedules, User user);
}
