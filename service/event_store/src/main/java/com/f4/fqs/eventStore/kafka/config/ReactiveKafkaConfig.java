package com.f4.fqs.eventStore.kafka.config;

import com.f4.fqs.commons.kafka_common.message.QueueCommand;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.SenderOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class ReactiveKafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String SERVER;

    /*@Bean
    public ReactiveKafkaConsumerTemplate<String, QueueCommand> reactiveKafkaTemplate() {

        return new ReactiveKafkaConsumerTemplate<String, QueueCommand> (ReactiveKafkaUtil.getInstance().receiverOptions());
    }*/


//    @Bean
//    public KafkaReceiver<String, QueueCommand> kafkaReceiver() {
//
//        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVER);
////        props.put(ConsumerConfig.GROUP_ID_CONFIG, "event-store-group");
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // 이전 메시지부터 조회
//
//        ReceiverOptions<String, QueueCommand> receiverOptions = ReceiverOptions.create(props);
//        return KafkaReceiver.create(receiverOptions);
//    }

}
