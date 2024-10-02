package com.f4.fqs.user.controller;


import com.f4.fqs.user.client.AuthServiceClient;
import com.f4.fqs.user.model.User;
import com.f4.fqs.user.dto.SignUpRequestDto;
import com.f4.fqs.user.dto.SignUpResponseDto;
import com.f4.fqs.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final AuthServiceClient authServiceClient;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignUpRequestDto requestDto, HttpServletResponse response){
        User user = userService.signup(requestDto);

        return ResponseEntity.ok(SignUpResponseDto.toResponse(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid SignUpRequestDto requestDto, HttpServletResponse response){
        User user = userService.login(requestDto);

        String jwt = authServiceClient.createAccessToken(user.getEmail());

        response.addHeader("Authorization", "Bearer " + jwt);

        return ResponseEntity.ok(SignUpResponseDto.toResponse(user));
    }
}
