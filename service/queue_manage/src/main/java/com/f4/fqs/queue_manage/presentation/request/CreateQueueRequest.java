package com.f4.fqs.queue_manage.presentation.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record CreateQueueRequest(
        @NotBlank @Size(max = 16)
        String name,
        @NotNull @Positive int messageRetentionPeriod,
        @NotNull @Positive int maxMessageSize,
        @NotNull @Future LocalDateTime expirationTime,
        @NotNull boolean messageOrderGuaranteed,
        @NotNull boolean messageDuplicationAllowed
) {
}
