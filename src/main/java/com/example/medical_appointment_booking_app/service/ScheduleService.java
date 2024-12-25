package com.example.medical_appointment_booking_app.service;

import com.example.medical_appointment_booking_app.entity.Schedule;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;

import java.security.Principal;

public interface ScheduleService {

    ResponseData<String> createSchedule (Principal principal, Long timeScheduleId);
}
