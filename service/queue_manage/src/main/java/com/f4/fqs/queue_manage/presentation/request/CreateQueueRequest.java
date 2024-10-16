package com.f4.fqs.queue_manage.presentation.request;

import com.f4.fqs.queue_manage.presentation.exception.QueueErrorCode;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record CreateQueueRequest(
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9-]{6,16}$", message = "큐 이름은 영문(대, 소문자), 숫자, 하이푼(-) 으로만 구성 가능합니다.")
        String name,
        @NotNull @Positive int messageRetentionPeriod,
        @NotNull @Positive int maxMessageSize,
        @NotNull @Future LocalDateTime expirationTime,
        @NotNull boolean messageOrderGuaranteed,
        @NotNull boolean messageDuplicationAllowed
) {
}
