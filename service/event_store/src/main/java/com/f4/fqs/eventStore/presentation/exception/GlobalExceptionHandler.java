package com.f4.fqs.eventStore.presentation.exception;

import com.f4.fqs.commons.domain.exception.BusinessException;
import com.f4.fqs.commons.domain.exception.ErrorCode;
import com.f4.fqs.commons.domain.response.FailedResponseBody;
import com.f4.fqs.commons.domain.response.ResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class) // custom 에러
    public ResponseEntity<ResponseBody<Void>> handleServiceException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus())
                .body(new FailedResponseBody(errorCode.getCode(), errorCode.getMessage()));
    }

    @ExceptionHandler(WebExchangeBindException.class) // Valid
    public ResponseEntity<ResponseBody<Void>> handleMethodArgumentNotValidException(
            WebExchangeBindException e) {
        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        log.error("MethodArgumentNotValidException : {}", errorMessage);
        return ResponseEntity.badRequest()
                .body(new FailedResponseBody(QueueErrorCode.INVALID_INPUT_VALUE.getCode(), errorMessage));
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