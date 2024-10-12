package com.f4.fqs.queue.domain.model.eventStore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class QueueCommand {

    private String userId;
    private EventType eventType;
    private LocalDateTime createdAt;

    public static QueueCommand addQueueCommand(String userId, LocalDateTime createdAt) {
        return new QueueCommand(userId, EventType.ADD_QUEUE, createdAt);
    }

    public static QueueCommand consumeQueueCommand(String userId, LocalDateTime createdAt) {
        return new QueueCommand(userId, EventType.CONSUME_QUEUE, createdAt);
    }

    public static QueueCommand finishWorkCommand(String userId, LocalDateTime createdAt) {
        return new QueueCommand(userId, EventType.FINISH_WORK, createdAt);
    }

}
