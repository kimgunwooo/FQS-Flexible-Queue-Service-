package com.f4.fqs.user.dto;

import com.f4.fqs.user.model.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpResponseDto {

    private final String message;
    private final String email;
    private final String password;
    private final String role;

    public static SignUpResponseDto toResponse(User user) {
        return SignUpResponseDto.builder()
                .message("회원가입 성공")
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

}
