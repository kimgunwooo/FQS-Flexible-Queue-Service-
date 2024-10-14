package com.f4.fqs.queue.presentation.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateQueueRequest(
        @NotBlank @Size(max = 16)
        String name,
        @NotNull int messageRetentionPeriod,
        @NotNull int maxMessageSize,
        @NotNull int expirationTime,
        @NotNull boolean messageOrderGuaranteed,
        @NotNull boolean messageDuplicationAllowed,
        @NotNull Long queuePackageId
) {
}
