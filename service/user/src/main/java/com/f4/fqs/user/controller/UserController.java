package com.f4.fqs.user.controller;


import com.f4.fqs.user.dto.LogInIAMRequestDto;
import com.f4.fqs.user.dto.LogInRequestDto;
import com.f4.fqs.user.dto.UserDto;
import com.f4.fqs.user.dto.SignUpRequestDto;
import com.f4.fqs.user.dto.createAccountRequest;
import com.f4.fqs.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/login/root")
    public ResponseEntity<UserDto> login(@RequestBody LogInRequestDto requestDto){

        UserDto userDto = userService.login(requestDto);

        return ResponseEntity.ok().body(userDto);
    }

    @PostMapping("/login/iam")
    public ResponseEntity<UserDto> login(@RequestBody LogInIAMRequestDto requestDto){

        UserDto userDto = userService.login(requestDto);

        return ResponseEntity.ok().body(userDto);
    }

    @PostMapping("/createIAM")
    public ResponseEntity<?> creatIAMAccount(@RequestBody createAccountRequest request){

        UserDto userDto = userService.createAccount(request);

        return ResponseEntity.ok().body(userDto);
    }

    @GetMapping("My-IAM")
    public ResponseEntity<?> getIAMAccounts(){

        List<UserDto> userDtoList = userService.getAccounts();

        return ResponseEntity.ok().body(userDtoList);
    }
}
