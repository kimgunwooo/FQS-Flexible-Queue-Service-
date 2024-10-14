package com.f4.fqs.user.dto.ROOT;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SignUpRequestDto {

    private String groupName;

    private String groupLeaderName;

    @NotBlank(message = "이메일을 입력하지 않았습니다.")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력하지 않았습니다.")
    private String password;
}