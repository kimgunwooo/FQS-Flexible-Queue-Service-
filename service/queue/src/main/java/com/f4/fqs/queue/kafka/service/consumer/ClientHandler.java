package com.f4.fqs.queue.kafka.service.consumer;

import com.f4.fqs.commons.domain.util.KafkaUtil;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientHandler {

    private final ApplicationContext applicationContext;
    private final Consumer consumer;
    @Qualifier("default-client-consumer-config")
    private final ConsumerFactory consumerFactory;

    /**
     *
     * @param size : Message 소모 요청량
     */
    public void consume(String serviceName, int size) {

        /**
         * TODO get bean name from redis
         */
        String beanName = "default-client-consumer";

//        Consumer<String, String> consumer = applicationContext.getBean(beanName, Consumer.class);
        Consumer consumer = consumerFactory.createConsumer();
        /**
         * TODO add exception handling
         */

        /**
         * TODO redis 에서 secretkey 를 이용해 대기열 정보 가져온 후 서비스 명 반환
         */

        String topicName = KafkaUtil.makeQueueTopicName(serviceName);
        System.out.println("topicName on Consumer = " + topicName);
        TopicPartition topicPartition = new TopicPartition(topicName, 0);

        consumer.assign(List.of(topicPartition));
        ;
        consumer.seek(topicPartition, 0 + size);

        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(5000));
        System.out.println(" ======== polling finish           =========");
        records.forEach(i -> {
            System.out.println("i = " + i);
        });

        consumer.close();

    }

}
