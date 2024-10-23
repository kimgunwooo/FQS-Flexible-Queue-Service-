package com.f4.fqs.queue_manage.application.response;

import com.f4.fqs.queue_manage.domain.model.Queue;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record QueueInfo(
        Long id,
        Long userId,
        String name,
        int messageRetentionPeriod,
        int maxMessageSize,
        LocalDateTime expirationTime,
        boolean messageOrderGuaranteed,
        boolean messageDuplicationAllowed,
        boolean isActive,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime createdAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime updatedAt
        // secretKey 는 정책상 처음 발급시에만 제공.
) {
    public static QueueInfo fromQueue(Queue queue) {
        return new QueueInfo(
                queue.getId(),
                queue.getUserId(),
                queue.getName(),
                queue.getMessageRetentionPeriod(),
                queue.getMaxMessageSize(),
                queue.getExpirationTime(),
                queue.isMessageOrderGuaranteed(),
                queue.isMessageDuplicationAllowed(),
                queue.getIsActive(),
                queue.getCreatedAt(),
                queue.getUpdatedAt()
        );
    }
}
