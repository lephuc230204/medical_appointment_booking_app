package com.example.medical_appointment_booking_app.payload.request.Dto;

import com.example.medical_appointment_booking_app.entity.Schedule;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Data
public class ScheduleDto {
    private Long scheduleId;
    private String doctorName;
    LocalTime startTime;
    LocalTime endTime;
    private Schedule.Status status;

    public static ScheduleDto toDto(Schedule schedule) {
        return ScheduleDto.builder()
                .scheduleId(schedule.getScheduleId())
                .status(schedule.getStatus())
                .doctorName(schedule.getUser().getUsername())
                .startTime(schedule.getTimeSchedule().getAppointmentStart())
                .endTime(schedule.getTimeSchedule().getAppointmentEnd())
                .build();
    }
}
