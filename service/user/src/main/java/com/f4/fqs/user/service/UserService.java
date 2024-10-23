package com.f4.fqs.user.service;


import static com.f4.fqs.user.exception.UserErrorCode.*;

import com.f4.fqs.commons.domain.exception.BusinessException;
import com.f4.fqs.user.dto.IAM.IAMUserDto;
import com.f4.fqs.user.dto.IAM.LogInIAMRequestDto;
import com.f4.fqs.user.dto.ROOT.LogInRequestDto;
import com.f4.fqs.user.dto.ROOT.RootUserDto;
import com.f4.fqs.user.dto.IAM.CreateAccountRequest;
import com.f4.fqs.user.model.IAMUser;
import com.f4.fqs.user.model.RootUser;
import com.f4.fqs.user.dto.ROOT.SignUpRequestDto;
import com.f4.fqs.user.model.UserRoleEnum;
import com.f4.fqs.user.repository.IAMRepository;
import com.f4.fqs.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
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
            throw new BusinessException(EMAIL_ALREADY_EXISTS);
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        RootUser rootUser = new RootUser(encodedPassword, requestDto);

        userRepository.save(rootUser);

        return RootUserDto.toResponse(rootUser);
    }

    //root 계정
    public RootUserDto login(LogInRequestDto requestDto) {

        RootUser rootUser = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new BusinessException(EMAIL_NOT_REGISTERED));

        if (!passwordEncoder.matches(requestDto.getPassword(), rootUser.getPassword())) {
            throw new BusinessException(INCORRECT_PASSWORD);
        }

        return RootUserDto.toResponse(rootUser);
    }

    //IAM 계정
    public IAMUserDto login(LogInIAMRequestDto requestDto) {

        IAMUser iamUser = iamRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new BusinessException(EMAIL_NOT_REGISTERED));

        if (!passwordEncoder.matches(requestDto.getPassword(), iamUser.getPassword())) {
            throw new BusinessException(INCORRECT_PASSWORD);
        }

        return IAMUserDto.toResponse(iamUser);
    }

    //root의 iam 계정 생성
    @Transactional
    public IAMUserDto createAccount(CreateAccountRequest request) {

        if (iamRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(EMAIL_ALREADY_EXISTS);
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        RootUser rootUser = userRepository.findById(request.getGroupId()).get();

        IAMUser iamUser = IAMUser
                .builder()
                .rootUser(rootUser)
                .email(request.getEmail())
                .name(request.getName())
                .password(encodedPassword)
                .role(UserRoleEnum.IAM)
                .build();

        iamRepository.save(iamUser);

        return IAMUserDto.toResponse(iamUser);
    }

    @Transactional
    public List<IAMUserDto> getAllUsers(Long groupId) {
        List<IAMUser> userList = iamRepository.findAllByRootUser_Id(groupId);

        return userList.stream()
                .map(IAMUserDto::toResponse)
                .collect(Collectors.toList());
    }
}
