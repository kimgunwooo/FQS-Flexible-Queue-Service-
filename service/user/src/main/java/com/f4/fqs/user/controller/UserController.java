package com.f4.fqs.user.controller;


import com.f4.fqs.user.dto.IAM.IAMUserDto;
import com.f4.fqs.user.dto.IAM.LogInIAMRequestDto;
import com.f4.fqs.user.dto.ROOT.LogInRequestDto;
import com.f4.fqs.user.dto.ROOT.RootUserDto;
import com.f4.fqs.user.dto.ROOT.SignUpRequestDto;
import com.f4.fqs.user.dto.IAM.CreateAccountRequest;
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
    public ResponseEntity<RootUserDto> signup(@RequestBody SignUpRequestDto requestDto){

        RootUserDto rootUserDto = userService.signup(requestDto);

    return ResponseEntity.ok().body(rootUserDto);
    }

    @PostMapping("/login/root")
    public ResponseEntity<RootUserDto> login(@RequestBody LogInRequestDto requestDto){

        RootUserDto rootUserDto = userService.login(requestDto);

        return ResponseEntity.ok().body(rootUserDto);
    }

    @PostMapping("/login/iam")
    public ResponseEntity<IAMUserDto> login(@RequestBody LogInIAMRequestDto requestDto){

        IAMUserDto iamUserDto = userService.login(requestDto);

        return ResponseEntity.ok().body(iamUserDto);
    }

    @PostMapping("/createIAM")
    public ResponseEntity<IAMUserDto> creatIAMAccount(@RequestBody CreateAccountRequest request){

        IAMUserDto userDto = userService.createAccount(request);

        return ResponseEntity.ok().body(userDto);
    }



//    @GetMapping("My-IAM")
//    public ResponseEntity<?> getIAMAccounts(){
//
//        List<UserDto> userDtoList = userService.getAccounts();
//
//        return ResponseEntity.ok().body(userDtoList);
//    }
}
