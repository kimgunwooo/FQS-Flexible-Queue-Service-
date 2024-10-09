package com.f4.fqs.user.service;

import static java.rmi.server.LogStream.log;

import com.f4.fqs.user.dto.IAM.IAMUserDto;
import com.f4.fqs.user.dto.IAM.LogInIAMRequestDto;
import com.f4.fqs.user.dto.ROOT.LogInRequestDto;
import com.f4.fqs.user.dto.ROOT.RootUserDto;
import com.f4.fqs.user.dto.IAM.CreateAccountRequest;
import com.f4.fqs.user.model.IAMUser;
import com.f4.fqs.user.model.RootUser;
import com.f4.fqs.user.dto.ROOT.SignUpRequestDto;
import com.f4.fqs.user.repository.IAMRepository;
import com.f4.fqs.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final IAMRepository iamRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public RootUserDto signup(SignUpRequestDto requestDto) {

        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        RootUser rootUser = new RootUser(encodedPassword, requestDto);

        userRepository.save(rootUser);

        return RootUserDto.toResponse(rootUser);
    }

    //root 계정
    public RootUserDto login(LogInRequestDto requestDto) {

        RootUser rootUser = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("등록된 이메일이 아닙니다."));

        if (!passwordEncoder.matches(requestDto.getPassword(), rootUser.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀립니다.");
        }

        return RootUserDto.toResponse(rootUser);
    }

    //IAM 계정
    public IAMUserDto login(LogInIAMRequestDto requestDto) {

        IAMUser iamUser = iamRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("등록된 이메일이 아닙니다."));

        if (!passwordEncoder.matches(requestDto.getPassword(), iamUser.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀립니다.");
        }

        return IAMUserDto.toResponse(iamUser);
    }

    //root의 iam 계정 생성
    public IAMUserDto createAccount(CreateAccountRequest request) {

        if (iamRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        String groupName = userRepository.findById(request.getGroupId()).get().getGroupName();

        log(groupName);

        IAMUser iamUser = IAMUser
                .builder()
                .groupId(request.getGroupId())
                .email(request.getEmail())
                .groupName(groupName)
                .name(request.getName())
                .password(encodedPassword)
                .role(request.getRole())
                .build();

        iamRepository.save(iamUser);

        return IAMUserDto.toResponse(iamUser);
    }

    public RootUserDto getUserInfo(Long id) {
        
        RootUser dto =  userRepository.findById(id).get();

        return  RootUserDto.toResponse(dto);
    }

//    public List<UserDto> getAccounts() {
//
//    }
}
