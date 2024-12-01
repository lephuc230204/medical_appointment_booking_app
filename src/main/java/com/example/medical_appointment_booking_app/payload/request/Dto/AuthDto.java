package com.example.medical_appointment_booking_app.payload.request.Dto;

import com.example.medical_appointment_booking_app.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthDto {
    private Long id;
    private String token;
    private String userName;
    private String status;
    private String result;

    public static AuthDto from(User user, String token, String status, String result) {
        return AuthDto.builder()
                .id(user.getUserId())
                .userName(user.getUsername())
                .token(token)
                .status(status)
                .result(result)
                .build();
    }
}
