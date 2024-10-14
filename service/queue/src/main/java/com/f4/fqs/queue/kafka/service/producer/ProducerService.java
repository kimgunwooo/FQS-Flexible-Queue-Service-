package com.f4.fqs.queue.kafka.service.producer;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ProducerService {

    void send(String topic, String message) throws JsonProcessingException;

}
