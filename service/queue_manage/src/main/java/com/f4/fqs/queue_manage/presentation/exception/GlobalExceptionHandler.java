package com.f4.fqs.queue_manage.presentation.exception;

import com.f4.fqs.commons.domain.exception.BusinessException;
import com.f4.fqs.commons.domain.exception.ErrorCode;
import com.f4.fqs.commons.domain.response.FailedResponseBody;
import com.f4.fqs.commons.domain.response.ResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class) // custom 에러
    public ResponseEntity<ResponseBody<Void>> handleServiceException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus())
                .body(new FailedResponseBody(errorCode.getCode(), errorCode.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // Valid
    public ResponseEntity<ResponseBody<Void>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        log.error("MethodArgumentNotValidException : {}", errorMessage);
        return ResponseEntity.badRequest()
                .body(new FailedResponseBody(QueueErrorCode.INVALID_INPUT_VALUE.getCode(), errorMessage));
    }

    @ExceptionHandler(IllegalArgumentException.class) // IllegalArgumentException
    public ResponseEntity<ResponseBody<Void>> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("IllegalArgumentException : {}", e.getMessage());
        return ResponseEntity.badRequest()
                .body(new FailedResponseBody(QueueErrorCode.INVALID_INPUT_VALUE.getCode(), e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseBody<Void>> handleException(Exception e) {
        log.error("Exception : {}", e.getMessage());
        return ResponseEntity.internalServerError()
                .body(new FailedResponseBody(
                        QueueErrorCode.INTERNAL_SERVER_ERROR.getCode(),
                        QueueErrorCode.INTERNAL_SERVER_ERROR.getMessage()));
    }
}