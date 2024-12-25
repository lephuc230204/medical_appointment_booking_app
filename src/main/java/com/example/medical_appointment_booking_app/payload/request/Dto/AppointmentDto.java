package com.example.medical_appointment_booking_app.payload.request.Dto;

import com.example.medical_appointment_booking_app.entity.Appointment;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Builder
@Data
public class AppointmentDto {
    private Long appointmentId;
    private Long userId;
    private Long doctorId;
    private Long scheduleId;
    private String reason;
    private LocalDate createdAt;
    private Appointment.Status status;

    public static AppointmentDto toDto(Appointment appointment) {
        return AppointmentDto.builder()
                .appointmentId(appointment.getAppointmentId())
                .userId(appointment.getUser().getUserId())
                .doctorId(appointment.getDoctor().getUserId())
                .scheduleId(appointment.getSchedule().getScheduleId())
                .reason(appointment.getReason())
                .createdAt(appointment.getCreateDate())
                .status(appointment.getStatus())
                .build();
    }
}
