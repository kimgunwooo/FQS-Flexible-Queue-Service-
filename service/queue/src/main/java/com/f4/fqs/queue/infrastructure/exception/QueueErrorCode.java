package com.f4.fqs.queue.infrastructure.exception;

import com.f4.fqs.commons.domain.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QueueErrorCode implements ErrorCode {

    NOT_EXIST_WAITING_INFO(400, "QUEUE_001", "대기열에 존재하지 않는 사용자입니다"),
    CONSUME_SIZE_MUST_BE_OVER_ZERO(400, "QUEUE_002", "대기열 소모 크기는 양수여야 합니다"),
    ;
    private final int status;
    private final String code;
    private final String message;

}
