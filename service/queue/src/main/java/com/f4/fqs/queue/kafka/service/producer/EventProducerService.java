package com.f4.fqs.queue.kafka.service.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static com.f4.fqs.commons.domain.util.ParsingUtil.makeJsonString;

@Service
public final class EventProducerService implements ProducerService {

    @Qualifier("eventSourcing-producer")
    private KafkaTemplate<String, String> kafkaTemplate;


    @Override
    public void send(String topic, String message) {

        CompletableFuture.runAsync(() -> kafkaTemplate.send(topic, message));
    }

}
