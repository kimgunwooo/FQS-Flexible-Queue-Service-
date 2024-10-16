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
    NOT_AUTHORIZATION(403, "SECURITY_001", "권한이 없습니다. - auth");

    private final int status;
    private final String code;
    private final String message;
}