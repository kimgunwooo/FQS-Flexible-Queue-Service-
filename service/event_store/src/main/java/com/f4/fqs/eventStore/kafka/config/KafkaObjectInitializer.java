package com.f4.fqs.eventStore.kafka.config;

import com.f4.fqs.commons.kafka_common.message.QueueCommand;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import reactor.kafka.receiver.ReceiverOptions;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;


//@Slf4j
//@Component
public class KafkaObjectInitializer {

    @Value("${spring.kafka.bootstrap-servers}")
    private String SERVER;



/*    public KafkaReceiver<String, QueueCommand> kafkaReceiver(String server, String topic) {

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, topic);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "none"); // 이전 메시지부터 조회

        ReceiverOptions<String, QueueCommand> receiverOptions = ReceiverOptions.create(props);
        receiverOptions.subscription(Collections.singleton(topic));
        return KafkaReceiver.create(receiverOptions);
    }*/

}
