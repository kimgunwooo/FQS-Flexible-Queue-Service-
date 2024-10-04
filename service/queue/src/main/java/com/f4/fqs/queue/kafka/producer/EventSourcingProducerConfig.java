package com.f4.fqs.queue.kafka.producer;


import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Map;

@Configuration
public class EventSourcingProducerConfig {

    @Bean("eventSourcing-producer")
    public KafkaTemplate<String, String> kafkaTemplate() {

        return new KafkaTemplate<>(
                new DefaultKafkaProducerFactory<>(
                        Map.of(
                            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
                            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                            ProducerConfig.ACKS_CONFIG, "all"
                        )
                )
        );
    }

}
