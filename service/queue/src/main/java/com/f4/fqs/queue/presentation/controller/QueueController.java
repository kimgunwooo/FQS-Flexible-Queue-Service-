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

import java.util.List;
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
    public Mono<ResponseEntity<ResponseBody<String>>> createQueue(@PathVariable String serviceName) {

        if(!Objects.equals(serverName, serviceName)) {
            return Mono.error(() -> new BusinessException(QueueErrorCode.INVALID_SERVER_REQUEST));
        }

        Mono<ResponseEntity<ResponseBody<String>>> result = queueService.lineUp()
                .map(response -> ResponseEntity.ok(createSuccessResponse(response)));

        return result;
    }

    @PostMapping("/consume")
    public Mono<ResponseEntity<ResponseBody<List<String>>>> consumeQueue(
            @PathVariable String serviceName,
            @RequestParam(defaultValue = "1") int size) {

        if(!Objects.equals(serverName, serviceName)) {
            return Mono.error(() -> new BusinessException(QueueErrorCode.INVALID_SERVER_REQUEST));
        }
        Mono<ResponseEntity<ResponseBody<List<String>>>> result = queueService.consume(size)
                .map(response -> ResponseEntity.ok(createSuccessResponse(response)));

        return result;
    }

    @GetMapping("/ranks")
    public Mono<ResponseEntity<ResponseBody<Long>>> getCurrentOrder(
            @PathVariable String serviceName,
            @RequestParam String identifier) {

        if(!Objects.equals(serverName, serviceName)) {
            return Mono.error(() -> new BusinessException(QueueErrorCode.INVALID_SERVER_REQUEST));
        }

        Mono<ResponseEntity<ResponseBody<Long>>> result = queueService.getCurrentOrder(identifier)
                .map(response -> ResponseEntity.ok(createSuccessResponse(response)));

        return result;
    }

}
