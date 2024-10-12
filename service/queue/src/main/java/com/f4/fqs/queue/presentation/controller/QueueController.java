package com.f4.fqs.queue.presentation.controller;

import com.f4.fqs.commons.domain.exception.BusinessException;
import com.f4.fqs.commons.domain.response.ResponseBody;
import com.f4.fqs.queue.application.response.AddQueueResponse;
import com.f4.fqs.queue.application.response.ConsumeQueueResponse;
import com.f4.fqs.queue.application.response.FindRankResponse;
import com.f4.fqs.queue.application.service.QueueService;
import com.f4.fqs.queue.infrastructure.exception.QueueErrorCode;
import com.f4.fqs.queue.presentation.request.AddQueueRequest;
import com.f4.fqs.queue.presentation.request.FindRankRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static com.f4.fqs.commons.domain.response.ResponseUtil.createSuccessResponse;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/queue")
public class QueueController {

    private final QueueService queueService;

    @PostMapping("/add")
    public Mono<ResponseEntity<ResponseBody<AddQueueResponse>>> createQueue() {

        Mono<ResponseEntity<ResponseBody<AddQueueResponse>>> result = queueService.lineUp()
                .map(response -> ResponseEntity.ok(createSuccessResponse(response)));

        return result;
    }

    @PostMapping("/consume")
    public Mono<ResponseEntity<ResponseBody<ConsumeQueueResponse>>> consumeQueue(
            @RequestParam(defaultValue = "1") int size) {

        Mono<ResponseEntity<ResponseBody<ConsumeQueueResponse>>> result = queueService.consume(size)
                .map(response -> ResponseEntity.ok(createSuccessResponse(response)));

        return result;
    }

    @PostMapping("/ranks")
    public Mono<ResponseEntity<ResponseBody<FindRankResponse>>> getCurrentOrder(
            @RequestBody FindRankRequest request) {

        Mono<ResponseEntity<ResponseBody<FindRankResponse>>> result = queueService.getCurrentOrder(request)
                .map(response -> ResponseEntity.ok(createSuccessResponse(response)));

        return result;
    }

}
