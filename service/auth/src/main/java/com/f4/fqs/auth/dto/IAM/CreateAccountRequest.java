package com.f4.fqs.auth.dto.IAM;

import lombok.Getter;
import lombok.Setter;

@Getter
public class CreateAccountRequest {

    @Setter
    private Long groupId;

    private String name;

    private String email;

    private String password;
}
