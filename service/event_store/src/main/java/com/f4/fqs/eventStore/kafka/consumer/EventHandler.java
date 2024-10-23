package com.f4.fqs.eventStore.kafka.consumer;

import com.f4.fqs.commons.domain.exception.BusinessException;
import com.f4.fqs.commons.kafka_common.exception.StoreErrorCode;
import com.f4.fqs.commons.kafka_common.message.EventType;
import com.f4.fqs.commons.kafka_common.message.QueueCommand;
import com.f4.fqs.eventStore.application.response.QueueStatusResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListOffsetsResult;
import org.apache.kafka.clients.admin.OffsetSpec;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventHandler {

    @Value("${spring.kafka.bootstrap-servers}")
    private String SERVER;

    private final ReactiveKafkaConsumerTemplate<String, QueueCommand> reactiveKafkaTemplate;
    private final KafkaReceiver<String, QueueCommand> kafkaReceiver;
    private final KStream<String, QueueCommand> kStream;
    private final StreamsBuilder builder;

    public Mono<QueueStatusResponse> replay(String serviceName, LocalDateTime from, LocalDateTime to) {

        try (AdminClient client = AdminClient.create(Map.of("bootstrap.servers", SERVER))) {

            TopicPartition topicPartition = new TopicPartition(serviceName, 0);
            ListOffsetsResult listOffsetsResult = client.listOffsets(Map.of(topicPartition, OffsetSpec.latest()));
            final OffsetManager offsetManager = new OffsetManager(listOffsetsResult.partitionResult(topicPartition).get().offset());;

            return kafkaReceiver
                    .receive()
                    .takeWhile(rec -> rec.partition() == 0
                            && rec.value().createdAt().isAfter(from) && rec.value().createdAt().isBefore(to)
                            && offsetManager.offset >= rec.offset() + 1
                    )
                    .filter(i -> i.value().serviceName().equals(serviceName))
                    .map(ConsumerRecord::value)
                    .collectList()
                    .map(i -> {
                        EventType status = EventType.START_QUEUE;
                        Set<UUID> set = new LinkedHashSet<>();
                        for (QueueCommand user : i) {
                            log.info("user :: {}", user);
                            switch (user.eventType()) {
                                case ADD_QUEUE -> set.add(user.userId());
                                case CONSUME_QUEUE -> set.remove(user.userId());
                                case START_QUEUE -> status = user.eventType();
                                case END_QUEUE -> status = user.eventType();
                                default -> {}
                            }
                        }
                        return new QueueStatusResponse(status == EventType.START_QUEUE ? "Running": "Finished", set);
                    });

        } catch (Exception e) {
            throw new BusinessException(StoreErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    @AllArgsConstructor
    class OffsetManager {
        public long offset = 0;
    }

}

/**
 * 4minute-2021
 * aws ssm start-session --target i-076b9e926eb4b8549
 * c0a96d87-211f-4f6a-8857-98f253ba4078
 * 16f40c65-a437-43d6-8523-dd50da42fba5
 *
 *
 *
 *
 * 4mimute-2024
 * 96ac37c6-976e-4051-9c1a-42e7eb11a14b
 * 72c17282-a497-4901-9f92-6fbacb006523
 * 4083fc17-02eb-4433-b1fd-3b7def52ba01
 * 9fe822a2-0a41-45e4-8eb0-3e2df1bae3ed
 * 8f042daa-1513-431c-b5dc-08e9d4ddf9f7
 *
 *
 * 4minute-2022
 * "7762a206-eb24-4d54-845a-82c50c88d9e6",
 * "96ac37c6-976e-4051-9c1a-42e7eb11a14b",
 * "72c17282-a497-4901-9f92-6fbacb006523",
 * "4083fc17-02eb-4433-b1fd-3b7def52ba01",
 * "9fe822a2-0a41-45e4-8eb0-3e2df1bae3ed",
 * "8f042daa-1513-431c-b5dc-08e9d4ddf9f7"
 *
 *
 */
