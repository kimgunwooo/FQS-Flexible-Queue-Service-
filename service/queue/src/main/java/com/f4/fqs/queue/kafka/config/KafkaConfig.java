package com.f4.fqs.queue.kafka.config;


import com.f4.fqs.commons.domain.message.QueueCommand;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;
import reactor.kafka.sender.SenderOptions;

import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    public ReactiveKafkaProducerTemplate<String, QueueCommand> reactiveKafkaTemplate() {

        return new ReactiveKafkaProducerTemplate<> (

                SenderOptions.create(
                        Map.of(
                            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092",
                            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class,
                            ProducerConfig.ACKS_CONFIG, "all"
                        )
                )
        );
    }

}
