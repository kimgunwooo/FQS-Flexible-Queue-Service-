package com.f4.fqs.queue_manage.application.response;

import java.time.LocalDateTime;

public record CloseQueueResponse(
        Long queueId,
        LocalDateTime expirationTime
){
}
