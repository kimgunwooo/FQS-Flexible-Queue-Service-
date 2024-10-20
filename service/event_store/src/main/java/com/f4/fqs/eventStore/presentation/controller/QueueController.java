package com.f4.fqs.eventStore.presentation.controller;

import com.f4.fqs.commons.domain.response.ResponseBody;
import com.f4.fqs.commons.domain.response.ResponseUtil;
import com.f4.fqs.commons.domain.response.SuccessResponseBody;
import com.f4.fqs.commons.kafka_common.message.QueueCommand;
import com.f4.fqs.eventStore.application.service.QueueService;
import com.f4.fqs.eventStore.kafka.consumer.EventHandler;
import com.f4.fqs.eventStore.presentation.request.EventRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.f4.fqs.commons.domain.response.ResponseUtil.createSuccessResponse;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/store")
public class QueueController {

    private final QueueService queueService;
    private final EventHandler eventHandler;

    @GetMapping
    public Mono<ResponseEntity<ResponseBody<List<QueueCommand>>>> event(
            @RequestParam String serviceName,
            @RequestParam LocalDateTime from,
            @RequestParam LocalDateTime to) {

        log.info("serviceName :: {}", serviceName);

        return eventHandler.consume(serviceName, from, to)
                .map(ResponseUtil::createSuccessResponse)
                .map(ResponseEntity::ok);

    }

}
