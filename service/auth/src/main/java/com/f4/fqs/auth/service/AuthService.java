package com.f4.fqs.auth.service;

import static com.f4.fqs.auth.exception.AuthErrorCode.*;

import com.f4.fqs.auth.client.UserServiceClient;
import com.f4.fqs.auth.dto.IAM.IAMUserDto;
import com.f4.fqs.auth.dto.IAM.LogInIAMRequestDto;
import com.f4.fqs.auth.dto.ROOT.LogInRequestDto;
import com.f4.fqs.auth.dto.ROOT.SignUpRequestDto;
import com.f4.fqs.auth.dto.ROOT.RootUserDto;
import com.f4.fqs.auth.dto.IAM.CreateAccountRequest;
import com.f4.fqs.auth.jwt.JwtUtil;
import com.f4.fqs.commons.domain.exception.BusinessException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserServiceClient userServiceClient;

    public String createAccessToken(Long id, String email, String role) {
        return jwtUtil.createAccessToken(id, email, role);
    }


    public RootUserDto signup(SignUpRequestDto requestDto) {

        try {
            return userServiceClient.signup(requestDto).getBody();
        }catch (Exception e){
            throw new BusinessException(FAIL_TO_SIGNUP);
        }
    }

    //root 계정 로그인
    public RootUserDto login(LogInRequestDto requestDto) {

        try{
            return userServiceClient.login(requestDto).getBody();
        }catch (Exception e){
            throw new BusinessException(FAIL_TO_ROOT_LOGIN);
        }
    }

    //IAM 계정 로그인
    public IAMUserDto login(LogInIAMRequestDto requestDto) {

        try {
            return userServiceClient.login(requestDto).getBody();
        }catch (Exception e){
            throw new BusinessException(FAIL_TO_IAM_LOGIN);
        }
    }

    public IAMUserDto createAccount(CreateAccountRequest request) {

        return userServiceClient.creatIAMAccount(request).getBody();
    }

    public List<IAMUserDto> getAllUsers(Long rootId) {
        return userServiceClient.getMembers(rootId).getBody();
    }
}