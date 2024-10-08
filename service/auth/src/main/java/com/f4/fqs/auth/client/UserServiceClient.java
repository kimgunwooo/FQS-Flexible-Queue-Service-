package com.f4.fqs.auth.client;

import com.f4.fqs.auth.dto.LogInIAMRequestDto;
import com.f4.fqs.auth.dto.LogInRequestDto;
import com.f4.fqs.auth.dto.SignUpRequestDto;
import com.f4.fqs.auth.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @PostMapping("/user/signup")
    ResponseEntity<UserDto> signup(@RequestBody SignUpRequestDto requestDto);

    @PostMapping("/user/login/root")
    ResponseEntity<UserDto> login(@RequestBody LogInRequestDto requestDto);

    @PostMapping("/user/login/iam")
    ResponseEntity<UserDto> login(@RequestBody LogInIAMRequestDto requestDto);

}
