package com.f4.fqs.user.dto.IAM;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LogInIAMRequestDto {

    private String email;
    private String password;
}
