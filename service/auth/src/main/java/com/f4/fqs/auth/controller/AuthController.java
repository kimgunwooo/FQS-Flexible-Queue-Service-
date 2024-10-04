package com.f4.fqs.auth.controller;

import com.f4.fqs.auth.dto.LogInRequestDto;
import com.f4.fqs.auth.dto.SignUpRequestDto;
import com.f4.fqs.auth.dto.UserDto;
import com.f4.fqs.auth.service.AuthService;
import com.f4.fqs.commons.domain.response.SuccessResponseBody;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<SuccessResponseBody<UserDto>> signup(@RequestBody @Valid SignUpRequestDto requestDto){

        UserDto userDto = authService.signup(requestDto);

        SuccessResponseBody<UserDto> responseBody = new SuccessResponseBody<>(userDto);

        return ResponseEntity.ok().body(responseBody);
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessResponseBody<UserDto>> login(@RequestBody @Valid LogInRequestDto requestDto, HttpServletResponse response){

        UserDto userDto = authService.login(requestDto);

        String jwt = authService.createAccessToken(requestDto.getEmail());

        response.addHeader("Authorization", "Bearer " + jwt);

        SuccessResponseBody<UserDto> responseBody = new SuccessResponseBody<>(userDto);

        return ResponseEntity.ok().body(responseBody);
    }
}
