package com.f4.fqs.queue.application.response;

import java.util.List;
import java.util.Map;

public record ConsumeQueueResponse(
        List<String> consumes
) {
}
