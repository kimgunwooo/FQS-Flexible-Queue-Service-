package com.f4.fqs.auth.client;

import com.f4.fqs.auth.dto.IAM.IAMUserDto;
import com.f4.fqs.auth.dto.IAM.LogInIAMRequestDto;
import com.f4.fqs.auth.dto.ROOT.LogInRequestDto;
import com.f4.fqs.auth.dto.ROOT.SignUpRequestDto;
import com.f4.fqs.auth.dto.ROOT.RootUserDto;
import com.f4.fqs.auth.dto.IAM.CreateAccountRequest;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @PostMapping("/user/signup")
    ResponseEntity<RootUserDto> signup(@RequestBody SignUpRequestDto requestDto);

    @PostMapping("/user/login/root")
    ResponseEntity<RootUserDto> login(@RequestBody LogInRequestDto requestDto);

    @PostMapping("/user/login/iam")
    ResponseEntity<IAMUserDto> login(@RequestBody LogInIAMRequestDto requestDto);

    @PostMapping("/user/createIAM")
    ResponseEntity<IAMUserDto> creatIAMAccount(@RequestBody CreateAccountRequest request);

    @GetMapping("user/showMembers")
    ResponseEntity<List<IAMUserDto>> getMembers(@RequestParam Long rootId);
}
