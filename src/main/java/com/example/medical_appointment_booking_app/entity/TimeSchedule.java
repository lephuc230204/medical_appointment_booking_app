package com.example.medical_appointment_booking_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "time_schedules")
public class TimeSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long timeScheduleId;

    @OneToMany(mappedBy = "timeSchedule")
    private List<Schedule> schedules;

    @Column(nullable = false)
    private LocalDate appointmentDate;// ngay hen

    @Column(nullable = false)
    private LocalTime appointmentStart; // gio bat dau

    @Column(nullable = false)
    private LocalTime appointmentEnd; // gio ket thuc
}
