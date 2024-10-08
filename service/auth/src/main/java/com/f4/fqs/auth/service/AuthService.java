package com.f4.fqs.auth.service;

import com.f4.fqs.auth.client.UserServiceClient;
import com.f4.fqs.auth.dto.LogInIAMRequestDto;
import com.f4.fqs.auth.dto.LogInRequestDto;
import com.f4.fqs.auth.dto.SignUpRequestDto;
import com.f4.fqs.auth.dto.UserDto;
import com.f4.fqs.auth.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserServiceClient userServiceClient;

    public String createAccessToken(String email) {
        return jwtUtil.createAccessToken(email);
    }


    public UserDto signup(SignUpRequestDto requestDto) {

        return userServiceClient.signup(requestDto).getBody();
    }

    //root 계정 로그인
    public UserDto login(LogInRequestDto requestDto) {

        return userServiceClient.login(requestDto).getBody();
    }

    //IAM 계정 로그인
    public UserDto login(LogInIAMRequestDto requestDto) {

        return userServiceClient.login(requestDto).getBody();
    }
}