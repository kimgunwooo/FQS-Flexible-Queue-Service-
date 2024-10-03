package com.f4.fqs.user.controller;


import com.f4.fqs.user.client.AuthServiceClient;
import com.f4.fqs.user.dto.LogInRequestDto;
import com.f4.fqs.user.dto.LogInResponseDto;
import com.f4.fqs.user.model.User;
import com.f4.fqs.user.dto.SignUpRequestDto;
import com.f4.fqs.user.dto.SignUpResponseDto;
import com.f4.fqs.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthServiceClient authServiceClient;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signup(@RequestBody @Valid SignUpRequestDto requestDto){
        User user = userService.signup(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(SignUpResponseDto.toResponse(user));
    }

    @PostMapping("/login")
    public ResponseEntity<LogInResponseDto> login(@RequestBody @Valid LogInRequestDto requestDto, HttpServletResponse response){
        User user = userService.login(requestDto);

        String jwt = authServiceClient.createAccessToken(user.getEmail());

        response.addHeader("Authorization", "Bearer " + jwt);

        return ResponseEntity.status(HttpStatus.OK).body(LogInResponseDto.toResponse(user));
    }
}
