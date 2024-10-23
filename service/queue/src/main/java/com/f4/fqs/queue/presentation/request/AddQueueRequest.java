package com.f4.fqs.queue.presentation.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AddQueueRequest(
        @NotNull Object userId
) {
}
