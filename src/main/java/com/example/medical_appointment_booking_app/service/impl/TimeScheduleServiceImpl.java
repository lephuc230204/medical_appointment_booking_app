package com.example.medical_appointment_booking_app.service.impl;

import com.example.medical_appointment_booking_app.entity.TimeSchedule;
import com.example.medical_appointment_booking_app.payload.request.Form.TimeScheduleForm;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;
import com.example.medical_appointment_booking_app.repository.TimeScheduleRepository;
import com.example.medical_appointment_booking_app.service.TimeScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TimeScheduleServiceImpl implements TimeScheduleService {
    @Autowired
    private TimeScheduleRepository timeScheduleRepository;

    @Override
    public ResponseData<String> createTimeSchedule(TimeScheduleForm form) {

        if(timeScheduleRepository.existsByAppointmentDate(form.getDate())) {
            throw new RuntimeException("Time schedule already exists");
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

        return new  ResponseData<>(200,"Time schedule created successfully");
    }
}
