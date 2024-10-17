package com.f4.fqs.user.exception;

import com.f4.fqs.commons.domain.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "COMMON_0001", "잘못된 입력 값입니다. - user"),
    INTERNAL_SERVER_ERROR(500, "COMMON_002", "서버 에러입니다. - user"),

    // Security
    NOT_AUTHORIZATION(403, "SECURITY_001", "권한이 없습니다. - user"),

    //User
    EMAIL_ALREADY_EXISTS(400, "USER_001","이미존재하는 이메일입니다. - user"),
    EMAIL_NOT_REGISTERED(404, "USER_002","등록되지 않은 이메일입니다. - user"),
    INCORRECT_PASSWORD(400, "USER_002","잘못된 비밀번호입니다. - user");


    private final int status;
    private final String code;
    private final String message;

}
