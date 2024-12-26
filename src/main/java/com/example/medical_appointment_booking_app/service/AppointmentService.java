package com.example.medical_appointment_booking_app.service;

import com.example.medical_appointment_booking_app.entity.Appointment;
import com.example.medical_appointment_booking_app.payload.request.Dto.AppointmentDto;
import com.example.medical_appointment_booking_app.payload.request.Form.AppointmentForm;
import com.example.medical_appointment_booking_app.payload.response.ResponseData;

import java.security.Principal;
import java.util.List;

public interface AppointmentService {

    ResponseData<AppointmentDto> createRequest(AppointmentForm appointmentForm, Principal principal );
    ResponseData<String> cancelRequest(Long appointmentID, Principal principal);
    ResponseData<List<AppointmentDto>> getMyAppointments(Principal principal);
}
