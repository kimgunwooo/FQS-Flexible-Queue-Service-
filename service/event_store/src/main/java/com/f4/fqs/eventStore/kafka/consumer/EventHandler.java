package com.f4.fqs.eventStore.kafka.consumer;

import com.f4.fqs.commons.kafka_common.message.QueueCommand;
import com.f4.fqs.eventStore.kafka.config.KafkaObjectInitializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.ReceiverOptions;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventHandler {

//    private final KafkaReceiver<String, QueueCommand> kafkaReceiver;
    private final ConcurrentKafkaListenerContainerFactory<String, QueueCommand> factory;
    private final KStream<String, QueueCommand> kStream;
    private final StreamsBuilder builder;



    public Mono<List<QueueCommand>> consume(String serviceName, LocalDateTime from, LocalDateTime to) {

        KafkaObjectInitializer kafkaObjectInitializer = new KafkaObjectInitializer();

        return kafkaObjectInitializer.reactiveKafkaTemplate(serviceName)
                .receive()
                .filter(rec -> rec.topic().equals(serviceName))
                .filter(rec -> rec.value().createdAt().isAfter(from) && rec.value().createdAt().isBefore(to))
                .doOnNext(rec -> rec.receiverOffset().acknowledge())
                .map(v -> {
                    log.info("v :: {}", v.value());
                    return v.value();
                })
                .collectList();


    }





}
