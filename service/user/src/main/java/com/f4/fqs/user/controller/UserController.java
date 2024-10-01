package com.f4.fqs.user.controller;


import com.f4.fqs.user.client.AuthServiceClient;
import com.f4.fqs.user.model.User;
import com.f4.fqs.user.dto.SignInRequestDto;
import com.f4.fqs.user.dto.SignupResponseDto;
import com.f4.fqs.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final AuthServiceClient authServiceClient;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignInRequestDto requestDto){
        User user = userService.signup(requestDto);

        String jwt = authServiceClient.createAccessToken(user.getEmail());

        return ResponseEntity.ok(new SignupResponseDto(user, jwt));
    }
}
