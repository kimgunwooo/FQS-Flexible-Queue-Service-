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
                    .map(ConsumerRecord::value)
                    .collectList()
                    .map(i -> {
                        EventType status = EventType.START_QUEUE;
                        Set<UUID> set = new LinkedHashSet<>();
                        for (QueueCommand user : i) {
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
