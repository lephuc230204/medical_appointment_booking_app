package com.example.medical_appointment_booking_app.payload.request.Dto;

import com.example.medical_appointment_booking_app.entity.User;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDto {
    private Long userId;
    private String username;
    private String email;
    private String phoneNumber;
    private String dob;

    public static UserDto toDto(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .username(user.getUsername())
                .phoneNumber(user.getPhoneNumber())
                .dob(builder().dob)
                .build();
    }
}
