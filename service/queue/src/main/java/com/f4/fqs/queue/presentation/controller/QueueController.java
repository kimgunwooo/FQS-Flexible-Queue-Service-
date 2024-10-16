package com.f4.fqs.queue.presentation.controller;

import com.f4.fqs.commons.domain.exception.BusinessException;
import com.f4.fqs.commons.domain.response.ResponseBody;
import com.f4.fqs.queue.application.response.AddQueueResponse;
import com.f4.fqs.queue.application.response.ConsumeQueueResponse;
import com.f4.fqs.queue.application.response.FindRankResponse;
import com.f4.fqs.queue.application.service.QueueService;
import com.f4.fqs.queue.presentation.exception.QueueErrorCode;
import com.f4.fqs.queue.presentation.request.FindRankRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static com.f4.fqs.commons.domain.response.ResponseUtil.createSuccessResponse;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/{serviceName}/queue")
public class QueueController {

    @Value("${spring.application.name}")
    private String serverName;

    private final QueueService queueService;

    @PostMapping("/add")
    public Mono<ResponseEntity<ResponseBody<AddQueueResponse>>> createQueue(@PathVariable String serviceName) {

        if(!Objects.equals(serverName, serviceName)) {
            return Mono.error(() -> new BusinessException(QueueErrorCode.INVALID_SERVER_REQUEST));
        }

        Mono<ResponseEntity<ResponseBody<AddQueueResponse>>> result = queueService.lineUp()
                .map(response -> ResponseEntity.ok(createSuccessResponse(response)));

        return result;
    }

    @PostMapping("/consume")
    public Mono<ResponseEntity<ResponseBody<ConsumeQueueResponse>>> consumeQueue(
            @PathVariable String serviceName,
            @RequestParam(defaultValue = "1") int size) {

        if(!Objects.equals(serverName, serviceName)) {
            return Mono.error(() -> new BusinessException(QueueErrorCode.INVALID_SERVER_REQUEST));
        }
        Mono<ResponseEntity<ResponseBody<ConsumeQueueResponse>>> result = queueService.consume(size)
                .map(response -> ResponseEntity.ok(createSuccessResponse(response)));

        return result;
    }

    @PostMapping("/ranks")
    public Mono<ResponseEntity<ResponseBody<FindRankResponse>>> getCurrentOrder(
            @PathVariable String serviceName,
            @RequestBody FindRankRequest request) {

        if(!Objects.equals(serverName, serviceName)) {
            return Mono.error(() -> new BusinessException(QueueErrorCode.INVALID_SERVER_REQUEST));
        }

        Mono<ResponseEntity<ResponseBody<FindRankResponse>>> result = queueService.getCurrentOrder(request)
                .map(response -> ResponseEntity.ok(createSuccessResponse(response)));

        return result;
    }

}
