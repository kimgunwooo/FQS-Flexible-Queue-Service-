package com.f4.fqs.auth.exception;

import com.f4.fqs.commons.domain.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "COMMON_0001", "잘못된 입력 값입니다. - auth"),
    INTERNAL_SERVER_ERROR(500, "COMMON_002", "서버 에러입니다. - auth"),

    // Security
    NOT_AUTHORIZATION(403, "SECURITY_001", "권한이 없습니다. - auth"),

    FAIL_TO_SIGNUP(400,"AUTH_001","회원가입 중 문제가 발생했습니다."),
    FAIL_TO_ROOT_LOGIN(400,"AUTH_002","Root 로그인 중 문제가 발생했습니다."),
    FAIL_TO_IAM_LOGIN(400, "AUTH_003", "IAM 로그인 중 문제가 발생했습니다. ");
    private final int status;
    private final String code;
    private final String message;
}
