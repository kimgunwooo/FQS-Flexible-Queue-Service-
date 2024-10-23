package com.f4.fqs.eventStore.kafka.config;


import com.f4.fqs.commons.domain.exception.BusinessException;
import com.f4.fqs.commons.kafka_common.exception.StoreErrorCode;
import com.f4.fqs.commons.kafka_common.message.QueueCommand;
import com.f4.fqs.commons.kafka_common.util.CommonConstraints;
import com.f4.fqs.commons.reactive_kafka.kafka.producer.EventSourcingExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.ForeachAction;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

@Slf4j
@Configuration
@EnableKafkaStreams
@RequiredArgsConstructor
public class KafkaStreamsConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String SERVER;

    private final KafkaAdmin kafkaAdmin;
    private final EventSourcingExecutor executor;
    private Set<String> topics = new HashSet<>();

    @Bean
    public Topology topology(StreamsBuilder streamsBuilder) {
        return streamsBuilder.build();
    }

    @Bean
    public KStream<String, QueueCommand> queueCommandKStream(StreamsBuilder streamsBuilder) {

        try (final AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {
            topics.addAll(adminClient.listTopics().names().get());
        } catch (InterruptedException|ExecutionException e) {
            throw new BusinessException(StoreErrorCode.FAILED_SEND_EVENT);
        }

        Consumed<String, QueueCommand> queueCommandConsumed = Consumed.with(Serdes.String(), new JsonSerde<>(QueueCommand.class));

        KStream<String, QueueCommand> stream = streamsBuilder.stream(
                CommonConstraints.EVENT,
                queueCommandConsumed
        );

        stream.foreach(
                (k, v) -> {

                if(!topics.contains(v.serviceName())) {
                    log.info("request create new topic :: {} ", v.serviceName());
                    kafkaAdmin.setAutoCreate(true);
                    kafkaAdmin.createOrModifyTopics(new NewTopic(v.serviceName(), 1, (short) 1));
                    topics.add(v.serviceName());
                }
                executor.divideEvent(v.serviceName(), v).subscribe();
            }
        );

        return stream;

    }

}
