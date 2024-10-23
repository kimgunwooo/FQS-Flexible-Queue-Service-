package com.f4.fqs.commons.reactive_kafka.kafka.producer;

import com.f4.fqs.commons.kafka_common.message.QueueCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import reactor.core.publisher.Mono;

public interface ProducerService {

    Mono<Void> send(String topic, QueueCommand message) throws JsonProcessingException;

}
