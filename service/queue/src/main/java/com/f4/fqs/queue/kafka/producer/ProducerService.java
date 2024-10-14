package com.f4.fqs.queue.kafka.producer;

import com.f4.fqs.commons.domain.message.QueueCommand;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ProducerService {

    void send(String topic, QueueCommand message) throws JsonProcessingException;

}
