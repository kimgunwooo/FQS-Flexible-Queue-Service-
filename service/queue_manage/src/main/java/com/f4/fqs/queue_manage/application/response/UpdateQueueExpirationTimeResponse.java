package com.f4.fqs.queue_manage.application.response;

import java.time.LocalDateTime;

public record UpdateQueueExpirationTimeResponse(
        Long queueId,
        LocalDateTime expirationTime
){
}
