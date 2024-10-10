package com.f4.fqs.queue_manage.presentation.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UpdateExpirationTimeRequest(
        @NotNull @Future LocalDateTime expirationTime
) {
}