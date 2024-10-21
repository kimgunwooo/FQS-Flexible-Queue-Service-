package com.f4.fqs.eventStore.kafka.consumer;

import com.f4.fqs.commons.domain.exception.BusinessException;
import com.f4.fqs.commons.kafka_common.exception.StoreErrorCode;
import com.f4.fqs.commons.kafka_common.message.QueueCommand;
import com.f4.fqs.eventStore.kafka.config.KafkaObjectInitializer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListOffsetsResult;
import org.apache.kafka.clients.admin.OffsetSpec;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.TopicPartitionInfo;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public Mono<List<QueueCommand>> consume(String serviceName, LocalDateTime from, LocalDateTime to) {

//        ReactiveKafkaConsumerTemplate<String, QueueCommand> template = reactiveKafkaTemplate;
//        reactiveKafkaTemplate.seek(new TopicPartition(serviceName, 0), 0);
//

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
    //                .filter(rec -> rec.topic().equals(serviceName))
    //                .filter(rec -> rec.value().createdAt().isAfter(from) && rec.value().createdAt().isBefore(to))
                    .map(ConsumerRecord::value)
                    .collectList();

        } catch (Exception e) {
            throw new BusinessException(StoreErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    @AllArgsConstructor
    class OffsetManager {
        public long offset = 0;
    }

}
