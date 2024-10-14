package com.f4.fqs.queue_manage.presentation.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record CreateQueueRequest(
        @NotBlank @Size(max = 16)
        String name,
        @NotNull int messageRetentionPeriod,
        @NotNull int maxMessageSize,
        @NotNull @Future LocalDateTime expirationTime,
        @NotNull boolean messageOrderGuaranteed,
        @NotNull boolean messageDuplicationAllowed
) {
}
