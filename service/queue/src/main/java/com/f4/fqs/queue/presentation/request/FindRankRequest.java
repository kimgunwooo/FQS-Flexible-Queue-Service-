package com.f4.fqs.queue.presentation.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record FindRankRequest(
    @NotNull UUID userId
) {
}
