package com.example.medical_appointment_booking_app.payload.request.Dto;

import com.example.medical_appointment_booking_app.entity.TimeSchedule;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class TimeScheduleDto {
    private Long timeScheduleId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<ScheduleDto> schedules;

    public static TimeScheduleDto toDto(TimeSchedule timeSchedule) {
        return TimeScheduleDto.builder()
                .timeScheduleId(timeSchedule.getTimeScheduleId())
                .date(timeSchedule.getAppointmentDate())
                .startTime(timeSchedule.getAppointmentStart())
                .endTime(timeSchedule.getAppointmentEnd())
                .schedules(timeSchedule.getSchedules() != null
                        ? timeSchedule.getSchedules().stream()
                        .map(ScheduleDto::toDto)
                        .collect(Collectors.toList())
                        : null)
                .build();
    }

}
