package com.f4.fqs.eventStore.application.response;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public record QueueStatusResponse(
        String activeStatus,
        Set<UUID> queue
) {
}
