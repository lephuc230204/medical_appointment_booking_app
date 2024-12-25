package com.example.medical_appointment_booking_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User user;

    @OneToMany( mappedBy = "schedule")
    private List<Appointment> appointments;

    @ManyToOne()
    @JoinColumn(name = "timeSchedule_id")
    private TimeSchedule timeSchedule;

    @Enumerated(EnumType.STRING)
    private Status status = Status.NOT_FULL;

    public boolean isFull(){
        return appointments.size() >= 15;
    }

    public void updateStatus() {
        if (isFull()) {
            this.status = Status.FULL;
        } else {
            this.status = Status.NOT_FULL;
        }
    }

    public enum Status {
        FULL,NOT_FULL
    }
}
