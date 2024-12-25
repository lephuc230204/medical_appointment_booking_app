package com.example.medical_appointment_booking_app.service;

import com.example.medical_appointment_booking_app.entity.TimeSchedule;
import com.example.medical_appointment_booking_app.payload.request.Dto.TimeScheduleDto;
import com.example.medical_appointment_booking_app.payload.request.Form.TimeScheduleForm;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;

import java.time.LocalDate;
import java.util.List;

public interface TimeScheduleService {

    ResponseData<String> createTimeSchedule(TimeScheduleForm form);
    ResponseData<List<TimeScheduleDto>> getByDate(LocalDate startDate);
}
