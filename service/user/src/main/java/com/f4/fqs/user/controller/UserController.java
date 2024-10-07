package com.f4.fqs.user.controller;


import com.f4.fqs.user.dto.LogInRequestDto;
import com.f4.fqs.user.dto.UserDto;
import com.f4.fqs.user.dto.SignUpRequestDto;
import com.f4.fqs.user.service.UserService;
import lombok.RequiredArgsConstructor;
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


    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignUpRequestDto requestDto){

        UserDto userDto = userService.signup(requestDto);

    return ResponseEntity.ok().body(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LogInRequestDto requestDto){

        UserDto userDto = userService.login(requestDto);

        return ResponseEntity.ok().body(userDto);
    }
}
