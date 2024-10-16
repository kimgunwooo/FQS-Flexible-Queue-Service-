package com.f4.fqs.queue_manage.presentation.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record CreateQueueRequest(
        @NotBlank
//        @Size(min = 6, max = 16)
        @Pattern(regexp = "^[a-zA-Z0-9-]$")
        String name,
        @NotNull int messageRetentionPeriod,
        @NotNull int maxMessageSize,
        @NotNull @Future LocalDateTime expirationTime,
        @NotNull boolean messageOrderGuaranteed,
        @NotNull boolean messageDuplicationAllowed
) {
}
