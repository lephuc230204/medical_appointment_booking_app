package com.example.medical_appointment_booking_app.service;

import com.example.medical_appointment_booking_app.payload.request.Dto.UserDto;
import com.example.medical_appointment_booking_app.payload.request.Form.UserForm;
import org.springframework.data.domain.Page;

public interface UserService {
    Page<UserDto> getUsers(int page, int size);
    UserDto createUser(UserForm form);
}
