package com.f4.fqs.eventStore.kafka.config;

import com.f4.fqs.commons.kafka_common.message.QueueCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class CustomJsonDeserializer extends JsonDeserializer<QueueCommand> {

    public CustomJsonDeserializer() {
        super(QueueCommand.class, false);
    }
}
