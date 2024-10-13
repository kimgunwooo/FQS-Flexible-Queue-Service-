package com.f4.fqs.auth.service;

import com.f4.fqs.auth.UserRoleEnum;
import com.f4.fqs.auth.client.UserServiceClient;
import com.f4.fqs.auth.dto.IAM.IAMUserDto;
import com.f4.fqs.auth.dto.IAM.LogInIAMRequestDto;
import com.f4.fqs.auth.dto.ROOT.LogInRequestDto;
import com.f4.fqs.auth.dto.ROOT.SignUpRequestDto;
import com.f4.fqs.auth.dto.ROOT.RootUserDto;
import com.f4.fqs.auth.dto.IAM.CreateAccountRequest;
import com.f4.fqs.auth.jwt.JwtUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserServiceClient userServiceClient;

    public String createAccessToken(Long id, String email, UserRoleEnum role) {
        return jwtUtil.createAccessToken(id, email, role);
    }


    public RootUserDto signup(SignUpRequestDto requestDto) {

        return userServiceClient.signup(requestDto).getBody();
    }

    //root 계정 로그인
    public RootUserDto login(LogInRequestDto requestDto) {

        return userServiceClient.login(requestDto).getBody();
    }

    //IAM 계정 로그인
    public IAMUserDto login(LogInIAMRequestDto requestDto) {

        return userServiceClient.login(requestDto).getBody();
    }

    public IAMUserDto createAccount(CreateAccountRequest request) {

        return userServiceClient.creatIAMAccount(request).getBody();
    }

    public List<IAMUserDto> getAllUsers(Long rootId) {
        return userServiceClient.getMembers(rootId).getBody();
    }
}