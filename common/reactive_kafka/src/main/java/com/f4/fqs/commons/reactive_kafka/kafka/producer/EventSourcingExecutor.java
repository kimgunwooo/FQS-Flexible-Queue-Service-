package com.f4.fqs.commons.reactive_kafka.kafka.producer;

import com.f4.fqs.commons.domain.exception.BusinessException;
import com.f4.fqs.commons.domain.util.CommonConstraints;
import com.f4.fqs.commons.kafka_common.exception.StoreErrorCode;
import com.f4.fqs.commons.kafka_common.message.QueueCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventSourcingExecutor {

    private final EventProducerService eventProducerService;

    public Mono<Void> createEvent(QueueCommand queueCommand) {
        log.info("send from producer :: {}", queueCommand);

        return Mono.fromRunnable(() -> eventProducerService.send(CommonConstraints.EVENT, queueCommand))
                .then(Mono.empty());
    }

    public Mono<Void> divideEvent(String topicName, QueueCommand queueCommand) {
        log.info("divide from event-store :: {}", queueCommand);

        return Mono.fromRunnable(() -> eventProducerService.send(topicName, queueCommand))
                .then(Mono.empty());
    }

}
