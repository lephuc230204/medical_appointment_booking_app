package com.example.medical_appointment_booking_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table( name = "appoinments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appointmentId;

    @ManyToOne
    @JoinColumn( name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn( name = "doctor_id", nullable = false)
    private User doctor;

    @ManyToOne
    @JoinColumn( name = "schedule_id", nullable = false)
    private Schedule schedule;

    @Column(nullable = true)
    private String reason;

    @Column(nullable = false)
    private LocalDate createDate;

    private Status status;

    private boolean isCome;

    private enum Status {
        PENDING,APPROVED,CANCELLED,COMPLETED
    }


}
