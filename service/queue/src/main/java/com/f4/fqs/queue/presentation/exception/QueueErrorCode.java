package com.f4.fqs.queue.presentation.exception;

import com.f4.fqs.commons.domain.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QueueErrorCode implements ErrorCode {
    // Common
    INVALID_INPUT_VALUE(400, "COMMON_0001", "잘못된 입력 값입니다."),
    INTERNAL_SERVER_ERROR(500, "COMMON_002", "서버 에러입니다."),
    INVALID_SERVER_REQUEST(400, "COMMON_0003", "요청과 서버 정보가 일치하지 않습니다."),

    // Security
    NOT_AUTHORIZATION(403, "SECURITY_001", "권한이 없습니다."),

    // Queue
    NOT_EXIST_WAITING_INFO(400, "QUEUE_001", "대기열에 존재하지 않는 사용자입니다"),
    CONSUME_SIZE_MUST_BE_OVER_ZERO(400, "QUEUE_002", "대기열 소모 크기는 양수여야 합니다"),
;

    private final int status;
    private final String code;
    private final String message;
}
