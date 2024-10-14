package com.f4.fqs.queue.kafka.service.producer;

import com.f4.fqs.commons.domain.util.KafkaUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ClientQueueExecutor implements ProducerService {

    private final ApplicationContext applicationContext;

    @Override
    public void send(String serviceName, String clientId) {

        /**
         * get bean name from redis
         */
        String producerName = "default-client-producer";
        KafkaTemplate templates = applicationContext.getBean(producerName, KafkaTemplate.class);
        /**
         * TODO add exception handling
         */

        /**
         * add on queue
         */
        System.out.println("send on clientId = " + clientId);

        String topicName = KafkaUtil.makeQueueTopicName(serviceName);
        System.out.println("topicName on Producer = " + topicName);
        CompletableFuture.runAsync(() -> templates.send(topicName, clientId));
    }

}
