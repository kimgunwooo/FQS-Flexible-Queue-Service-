package com.f4.fqs.commons.domain.util;

import static com.f4.fqs.commons.domain.util.CommonConstraints.*;

public final class KafkaUtil {
    private KafkaUtil() {}

    public static String makeEventTopicName(String topic) {
        return KAFKA_TOPIC_EVENT_PREFIX + topic;
    }

    public static String makeQueueTopicName(String topic) {
        return KAFKA_TOPIC_QUEUE_PREFIX + topic;
    }

}
