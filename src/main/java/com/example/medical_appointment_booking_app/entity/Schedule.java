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
@Table(name = "schedules")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @OneToMany( mappedBy = "schedule")
    private List<Appoinment> appointment;

    @ManyToOne()
    @JoinColumn(name = "timeSchedule_id")
    private TimeSchedule timeSchedule;

    private boolean isFull(){
        return appointment.size() >= 15;
    }
}
