package com.f4.fqs.queue.kafka.producer;

import com.f4.fqs.commons.domain.message.QueueCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public final class EventProducerService implements ProducerService {

    private final ReactiveKafkaProducerTemplate<String, QueueCommand> producer;
//    private final KafkaTemplate kafkaTemplate;
//    private final KafkaTemplate<String, QueueCommand> kafkaTemplate;

    @Override
    public void send(String topic, QueueCommand message) {

//        CompletableFuture.runAsync(() -> producer.send(topic, message))
//                .handle((o, e) -> {
//                    if(!Objects.isNull(e)) {
//                        log.error("이벤트 생성 에러 발생 :: {}", e.getMessage());
//                    }
//                    return null;
//                });
        producer.send(topic, message).doOnError(e -> {
            if (!Objects.isNull(e)) {
                log.error("이벤트 생성 에러 발생 :: {}", e.getMessage());
            }
        })
        .subscribe();
    }

}
