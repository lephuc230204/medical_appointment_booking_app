package com.example.medical_appointment_booking_app.service;

import com.example.medical_appointment_booking_app.entity.Schedule;
import com.example.medical_appointment_booking_app.payload.request.Dto.AppointmentDto;
import com.example.medical_appointment_booking_app.payload.request.Dto.ScheduleDto;
import com.example.medical_appointment_booking_app.payload.request.Dto.TableTimeDto;
import com.example.medical_appointment_booking_app.payload.request.Form.TableTimeForm;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {

    ResponseData<String> createSchedule (Principal principal, Long timeScheduleId);
    ResponseData<List<ScheduleDto>> getByDoctorIdAndDate (Long doctorId, LocalDate date);
    ResponseData<List<ScheduleDto>> getMySchedule (Principal principal, LocalDate date);
    ResponseData<List<AppointmentDto>> getListAppointmentInSchedule (Long scheduleId);
    ResponseData<List<TableTimeDto>> getMyScheduleInWeek(Principal principal, TableTimeForm form);
}
