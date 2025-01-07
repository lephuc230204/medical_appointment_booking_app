package com.example.medical_appointment_booking_app.payload.request.Dto;

import com.example.medical_appointment_booking_app.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class UserBasicDto {
    private String username;
    private String email;
    private String phoneNumber;
    private LocalDate dob;
    private List<AddressDto> addresses;

    public static UserBasicDto fromUser(User user) {
        return UserBasicDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .dob(user.getDob())
                .addresses(user.getAddresses().stream()
                        .map(AddressDto::fromEntity)
                        .collect(Collectors.toList())
                )
                .build();


    }
}
