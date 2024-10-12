package com.f4.fqs.queue.kafka.service.producer;

import com.f4.fqs.commons.domain.util.KafkaUtil;
import com.f4.fqs.queue.domain.model.eventStore.QueueCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.f4.fqs.commons.domain.util.ParsingUtil.makeJsonString;


@Service
@RequiredArgsConstructor
public class EventSourcingExecutor {

    private final EventProducerService eventProducerService;

    public void addCommand(String serviceName, String userId) {
        eventProducerService.send(KafkaUtil.makeEventTopicName(serviceName), makeJsonString(QueueCommand.addQueueCommand(userId, LocalDateTime.now())));
    }

    public void consumeQueue(String serviceName, String userId) {
        eventProducerService.send(KafkaUtil.makeEventTopicName(serviceName), makeJsonString(QueueCommand.consumeQueueCommand(userId, LocalDateTime.now())));
    }

    public void finishWork(String serviceName, String userId) {
        eventProducerService.send(KafkaUtil.makeEventTopicName(serviceName), makeJsonString(QueueCommand.finishWorkCommand(userId, LocalDateTime.now())));
    }

}
