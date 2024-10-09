package com.f4.fqs.auth.controller;

import com.f4.fqs.auth.UserRoleEnum;
import com.f4.fqs.auth.dto.IAM.IAMUserDto;
import com.f4.fqs.auth.dto.IAM.LogInIAMRequestDto;
import com.f4.fqs.auth.dto.ROOT.LogInRequestDto;
import com.f4.fqs.auth.dto.ROOT.SignUpRequestDto;
import com.f4.fqs.auth.dto.ROOT.RootUserDto;
import com.f4.fqs.auth.dto.IAM.CreateAccountRequest;
import com.f4.fqs.auth.jwt.JwtAuthentication;
import com.f4.fqs.auth.jwt.JwtUtil;
import com.f4.fqs.auth.service.AuthService;
import com.f4.fqs.commons.domain.response.FailedResponseBody;
import com.f4.fqs.commons.domain.response.SuccessResponseBody;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequestDto requestDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            FailedResponseBody failedResponseBody = new FailedResponseBody("400", errorMessage);
            return ResponseEntity.badRequest().body(failedResponseBody);
        }

        RootUserDto rootUserDto = authService.signup(requestDto);

        SuccessResponseBody<RootUserDto> responseBody = new SuccessResponseBody<>(rootUserDto);

        return ResponseEntity.ok().body(responseBody);
    }

    @PostMapping("/login/root")
    public ResponseEntity<?> loginRoot(@RequestBody @Valid LogInRequestDto requestDto, HttpServletResponse response, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            FailedResponseBody failedResponseBody = new FailedResponseBody("400", errorMessage);
            return ResponseEntity.badRequest().body(failedResponseBody);
        }

        RootUserDto rootUserDto = authService.login(requestDto);

        String jwt = authService.createAccessToken(rootUserDto.getId(), requestDto.getEmail(), UserRoleEnum.ROOT);

        response.addHeader("Authorization", jwt);

        SuccessResponseBody<RootUserDto> responseBody = new SuccessResponseBody<>(rootUserDto);

        return ResponseEntity.ok().body(responseBody);
    }

    @PostMapping("/login/iam")
    public ResponseEntity<?> loginIam(@RequestBody @Valid LogInIAMRequestDto requestDto, HttpServletResponse response, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            FailedResponseBody failedResponseBody = new FailedResponseBody("400", errorMessage);
            return ResponseEntity.badRequest().body(failedResponseBody);
        }

        IAMUserDto iamUserDto = authService.login(requestDto);

        String jwt = authService.createAccessToken(iamUserDto.getId(), requestDto.getEmail(), UserRoleEnum.IAM);

        response.addHeader("Authorization", jwt);

        SuccessResponseBody<IAMUserDto> responseBody = new SuccessResponseBody<>(iamUserDto);

        return ResponseEntity.ok().body(responseBody);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/createIAM")
    public ResponseEntity<?> creatIAMAccount(@RequestBody CreateAccountRequest request,
            @AuthenticationPrincipal JwtAuthentication jwtAuthentication) {

        Long userId = jwtAuthentication.getUserId();

        request.setGroupId(userId);

        IAMUserDto userDto = authService.createAccount(request);

        return ResponseEntity.ok().body(userDto);
    }
}
