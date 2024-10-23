package com.f4.fqs.auth.dto.ROOT;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SignUpRequestDto {

    @NotBlank(message = "그룹 이름을 입력하지 않았습니다.")
    private String groupName;

    @NotBlank(message = "그룹장 이름을 입력하지 않았습니다.")
    private String groupLeaderName;

    @NotBlank(message = "이메일을 입력하지 않았습니다.")
    @Pattern(regexp="^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])+[.][a-zA-Z]{2,3}$", message="이메일 주소 양식을 확인해주세요")
    private String email;


    @NotBlank(message = "비밀번호를 입력하지 않았습니다.")
    private String password;

}
