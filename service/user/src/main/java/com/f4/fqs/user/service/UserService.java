package com.f4.fqs.user.service;

import com.f4.fqs.user.model.User;
import com.f4.fqs.user.dto.SignInRequestDto;
import com.f4.fqs.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public User signup(SignInRequestDto requestDto) {

        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User user = new User(requestDto.getEmail(), encodedPassword, requestDto.getRole());
        return userRepository.save(user);
    }
}
