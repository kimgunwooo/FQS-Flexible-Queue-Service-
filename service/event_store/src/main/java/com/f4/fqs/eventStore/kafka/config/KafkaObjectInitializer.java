package com.f4.fqs.eventStore.kafka.config;

import com.f4.fqs.commons.kafka_common.message.QueueCommand;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class KafkaObjectInitializer {

    @Value("${spring.kafka.bootstrap-servers}")
    private String SERVER;

    private Map<String, Object> CONSUMER_OPTIONS_MAP = new HashMap<>(
            Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVER,
                //        config.put(ConsumerConfig.GROUP_ID_CONFIG, "your-group-id");
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class,
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"
            )
    );

    public ReactiveKafkaConsumerTemplate<String, QueueCommand> reactiveKafkaTemplate(String topic) {

        ReceiverOptions<String, QueueCommand> receiverOptions = ReceiverOptions.create(CONSUMER_OPTIONS_MAP);
        receiverOptions = receiverOptions.subscription(Collections.singleton(topic));
        ReactiveKafkaConsumerTemplate<String, QueueCommand> template = new ReactiveKafkaConsumerTemplate<>(receiverOptions);

        return template;
    }

}
