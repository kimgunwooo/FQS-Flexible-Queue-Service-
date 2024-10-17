package com.f4.fqs.auth.exception;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FailedResponseBody> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        FailedResponseBody response = new FailedResponseBody("400", errorMessage);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseBody<Void>> handleException(Exception e) {
        log.error("Exception : {}", e.getMessage());
        return ResponseEntity.internalServerError()
                .body(new FailedResponseBody(
                        AuthErrorCode.INTERNAL_SERVER_ERROR.getCode(),
                        AuthErrorCode.INTERNAL_SERVER_ERROR.getMessage()));
    }
}
