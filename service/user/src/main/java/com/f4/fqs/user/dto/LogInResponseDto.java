package com.f4.fqs.user.dto;

import com.f4.fqs.user.model.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LogInResponseDto {

    private final String message;
    private final String email;

    public static LogInResponseDto toResponse(User user) {
        return LogInResponseDto.builder()
                .message("로그인 성공")
                .email(user.getEmail())
                .build();
    }
}
