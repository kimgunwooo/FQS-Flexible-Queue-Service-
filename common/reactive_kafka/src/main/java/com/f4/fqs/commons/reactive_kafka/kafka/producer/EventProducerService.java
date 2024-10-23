package com.f4.fqs.commons.reactive_kafka.kafka.producer;

import com.f4.fqs.commons.domain.exception.BusinessException;
import com.f4.fqs.commons.kafka_common.exception.StoreErrorCode;
import com.f4.fqs.commons.kafka_common.message.QueueCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public final class EventProducerService implements ProducerService {

    private final ReactiveKafkaProducerTemplate<String, QueueCommand> producer;

    @Override
    public Mono<Void> send(String topic, QueueCommand message) {

        return producer.send(topic, topic, message)
                .doOnError(e -> log.error("이벤트 생성 에러 발생 :: {}", e.getMessage()))
                .onErrorMap(e -> new BusinessException(StoreErrorCode.FAILED_SEND_EVENT))
                .then(Mono.empty());
    }

}
