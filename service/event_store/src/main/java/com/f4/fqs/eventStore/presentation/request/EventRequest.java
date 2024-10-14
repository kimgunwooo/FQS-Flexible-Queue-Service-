package com.f4.fqs.eventStore.presentation.request;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventRequest (
        String queueName,
        EventRequest eventType,
        UUID userId,
        LocalDateTime createdAt
) {
}
