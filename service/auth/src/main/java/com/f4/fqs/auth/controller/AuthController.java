package com.f4.fqs.auth.controller;

import com.f4.fqs.auth.dto.LogInRequestDto;
import com.f4.fqs.auth.dto.SignUpRequestDto;
import com.f4.fqs.auth.dto.UserDto;
import com.f4.fqs.auth.service.AuthService;
import com.f4.fqs.commons.domain.response.FailedResponseBody;
import com.f4.fqs.commons.domain.response.SuccessResponseBody;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequestDto requestDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            FailedResponseBody failedResponseBody = new FailedResponseBody("400", errorMessage);
            return ResponseEntity.badRequest().body(failedResponseBody);
        }

        UserDto userDto = authService.signup(requestDto);

        SuccessResponseBody<UserDto> responseBody = new SuccessResponseBody<>(userDto);

        return ResponseEntity.ok().body(responseBody);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LogInRequestDto requestDto, HttpServletResponse response, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            FailedResponseBody failedResponseBody = new FailedResponseBody("400", errorMessage);
            return ResponseEntity.badRequest().body(failedResponseBody);
        }

        UserDto userDto = authService.login(requestDto);

        String jwt = authService.createAccessToken(requestDto.getEmail());

        response.addHeader("Authorization", "Bearer " + jwt);

        SuccessResponseBody<UserDto> responseBody = new SuccessResponseBody<>(userDto);

        return ResponseEntity.ok().body(responseBody);
    }
}
