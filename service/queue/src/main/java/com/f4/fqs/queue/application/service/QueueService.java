package com.f4.fqs.queue.application.service;

import com.f4.fqs.commons.domain.exception.BusinessException;
import com.f4.fqs.commons.kafka_common.message.QueueCommand;
import com.f4.fqs.commons.reactive_kafka.kafka.producer.EventSourcingExecutor;
import com.f4.fqs.queue.presentation.exception.QueueErrorCode;
import com.f4.fqs.queue.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class QueueService {

    private final RedisService redisService;
    private final EventSourcingExecutor executor;

    @Value("${spring.application.name}")
    private String SERVICE_NAME;


    public Mono<String> createUserIdAndAddOnQueue() {

        UUID userId = UUID.randomUUID();

        return redisService.lineUp(userId)
                .filter(Boolean::booleanValue)
                .then(executor.createEvent(QueueCommand.addQueueCommand(SERVICE_NAME, userId, LocalDateTime.now())))
                .thenReturn(userId.toString());

    }

    public Flux<String> consume(int size) {



        return redisService.consume(size)
                .delayUntil(userId -> executor.createEvent(
                    QueueCommand.consumeQueueCommand(SERVICE_NAME, UUID.fromString(userId), LocalDateTime.now())
                ));
    }




    public Mono<Long> getCurrentOrder(String identifier) {

        return redisService.getMyRank(identifier);

    }
}
