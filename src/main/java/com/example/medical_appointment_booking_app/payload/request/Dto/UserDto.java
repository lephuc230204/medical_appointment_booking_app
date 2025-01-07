package com.example.medical_appointment_booking_app.payload.request.Dto;

import com.example.medical_appointment_booking_app.entity.Role;
import com.example.medical_appointment_booking_app.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class UserDto {
    private Long userId;
    private String username;
    private String email;
    private String phoneNumber;
    private LocalDate dob;
    private List<AddressDto> addresses;
    private User.Status statusAccount;
    private LocalDate createdAt;
    private String role;

    public static UserDto toDto(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .role(user.getRole().getName())
                .email(user.getEmail())
                .username(user.getUsername())
                .phoneNumber(user.getPhoneNumber())
                .dob(user.getDob())
                .addresses(user.getAddresses().stream()
                        .map(AddressDto::fromEntity)
                        .collect(Collectors.toList())
                )
                .createdAt(user.getSetCreatedDate())
                .statusAccount(user.getStatus())
                .build();
    }
}
