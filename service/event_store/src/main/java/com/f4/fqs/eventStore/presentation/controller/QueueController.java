package com.f4.fqs.eventStore.presentation.controller;

import com.f4.fqs.commons.domain.response.ResponseBody;
import com.f4.fqs.commons.domain.response.ResponseUtil;
import com.f4.fqs.eventStore.application.response.QueueStatusResponse;
import com.f4.fqs.eventStore.kafka.consumer.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static com.f4.fqs.commons.domain.response.ResponseUtil.createSuccessResponse;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/store")
public class QueueController {

    private final EventHandler eventHandler;

    @GetMapping
    public Mono<ResponseEntity<ResponseBody<QueueStatusResponse>>> event(
            @RequestParam String serviceName,
            @RequestParam LocalDateTime from,
            @RequestParam LocalDateTime to) {

        log.info("queue [{}]'s status at [{}]", serviceName, to);

        return eventHandler.replay(serviceName, from, to)
                .map(ResponseUtil::createSuccessResponse)
                .map(ResponseEntity::ok);

    }

}
