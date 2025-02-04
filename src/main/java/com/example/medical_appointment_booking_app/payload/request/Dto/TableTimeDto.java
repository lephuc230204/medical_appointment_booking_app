package com.example.medical_appointment_booking_app.payload.request.Dto;

import com.example.medical_appointment_booking_app.entity.Schedule;
import com.example.medical_appointment_booking_app.entity.TimeSchedule;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class TableTimeDto {
    private LocalDate date;
    private List<ScheduleDto> schedules;

    public static TableTimeDto toDto(TimeSchedule timeSchedule) {
        return TableTimeDto.builder()
                .date(timeSchedule.getAppointmentDate())
                .schedules(timeSchedule.getSchedules().stream()
                        .map(ScheduleDto::toDto)
                        .collect(Collectors.toList()))
                .build();
    }

}
