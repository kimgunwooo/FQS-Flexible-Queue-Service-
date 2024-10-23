package com.f4.fqs.commons.domain.exception;

public interface ErrorCode {
    int getStatus();

    String getCode();

    String getMessage();
}
