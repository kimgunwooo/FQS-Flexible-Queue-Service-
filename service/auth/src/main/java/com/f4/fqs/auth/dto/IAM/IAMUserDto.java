package com.f4.fqs.auth.dto.IAM;

import lombok.Getter;

@Getter
public class IAMUserDto {
    private String groupName;
    private Long id;
    private String name;
    private String email;
    private String password;
    private String role;
}
