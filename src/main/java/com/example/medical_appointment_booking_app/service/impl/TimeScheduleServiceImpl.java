package com.example.medical_appointment_booking_app.service.impl;

import com.example.medical_appointment_booking_app.entity.DailyAppointmentStats;
import com.example.medical_appointment_booking_app.entity.TimeSchedule;
import com.example.medical_appointment_booking_app.exception.TimeScheduleIsExist;
import com.example.medical_appointment_booking_app.payload.request.Dto.TimeScheduleDto;
import com.example.medical_appointment_booking_app.payload.request.Form.TimeScheduleForm;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import com.example.medical_appointment_booking_app.repository.DailyAppointmentStatsRepository;
import com.example.medical_appointment_booking_app.repository.TimeScheduleRepository;
import com.example.medical_appointment_booking_app.service.TimeScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeScheduleServiceImpl implements TimeScheduleService {
    @Autowired
    private TimeScheduleRepository timeScheduleRepository;
    @Autowired
    private DailyAppointmentStatsRepository dailyAppointmentStatsRepository;

    @Override
    public ResponseData<String> createTimeSchedule(TimeScheduleForm form) {

        if(timeScheduleRepository.existsByAppointmentDate(form.getDate())) {
            throw new TimeScheduleIsExist("Time schedule already exists");
        }
        List<TimeSchedule> timeSchedules = new ArrayList<>();

        timeSchedules.add(TimeSchedule.builder().appointmentDate(form.getDate())
                .appointmentStart(LocalTime.of(7, 0))
                .appointmentEnd(LocalTime.of(9, 0))
                .build());
        timeSchedules.add(TimeSchedule.builder().appointmentDate(form.getDate())
                .appointmentStart(LocalTime.of(9, 0))
                .appointmentEnd(LocalTime.of(11, 0))
                .build());
        timeSchedules.add(TimeSchedule.builder().appointmentDate(form.getDate())
                .appointmentStart(LocalTime.of(13, 0))
                .appointmentEnd(LocalTime.of(15, 0))
                .build());
        timeSchedules.add(TimeSchedule.builder().appointmentDate(form.getDate())
                .appointmentStart(LocalTime.of(15, 0))
                .appointmentEnd(LocalTime.of(17, 0))
                .build());
        timeSchedules.add(TimeSchedule.builder().appointmentDate(form.getDate())
                .appointmentStart(LocalTime.of(18, 0))
                .appointmentEnd(LocalTime.of(20, 0))
                .build());
        timeScheduleRepository.saveAll(timeSchedules);

        DailyAppointmentStats dailyAppointmentStats = new DailyAppointmentStats();
        dailyAppointmentStats.setDate(form.getDate());
        dailyAppointmentStats.setTotalAppointments(0L);
        dailyAppointmentStats.setCompletedAppointments(0L);
        dailyAppointmentStats.setCancelledAppointments(0L);
        dailyAppointmentStatsRepository.save(dailyAppointmentStats);
        return new  ResponseData<>(200,"Time schedule created successfully");
    }

    @Override
    public ResponseData<List<TimeScheduleDto>> getByDate(LocalDate date) {

        List<TimeSchedule> timeSchedules  = timeScheduleRepository.findAllByAppointmentDate(date);
        if (timeSchedules.isEmpty()) {
            return new ResponseData<>(404, "No time schedules found for the specified date", null);
        }
        List<TimeScheduleDto> timeScheduleDtos = timeSchedules.stream()
                .map(TimeScheduleDto::toDto)
                .collect(Collectors.toList());

        return new ResponseData<>(200,"get successfully",timeScheduleDtos);
    }
}
