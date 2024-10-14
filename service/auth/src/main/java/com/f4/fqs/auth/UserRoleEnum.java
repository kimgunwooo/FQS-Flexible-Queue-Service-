package com.f4.fqs.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRoleEnum {
    ROOT(Authority.ROOT),
    IAM(Authority.IAM);

    private final String authority;

    public static class Authority{
        public static final String ROOT = "ROLE_ROOT";
        public static final String IAM = "ROLE_IAM";
    }

}
