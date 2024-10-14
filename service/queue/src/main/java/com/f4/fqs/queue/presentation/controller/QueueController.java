package com.f4.fqs.queue.presentation.controller;

import com.f4.fqs.commons.domain.response.ResponseBody;
import com.f4.fqs.queue.application.response.CreateQueueResponse;
import com.f4.fqs.queue.application.service.QueueService;
import com.f4.fqs.queue.presentation.request.CreateQueueRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static com.f4.fqs.commons.domain.response.ResponseUtil.createSuccessResponse;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/queue")
public class QueueController {

    private final QueueService queueService;

    @PostMapping
    public Mono<ResponseEntity<ResponseBody<CreateQueueResponse>>> createQueue(@RequestBody CreateQueueRequest request) throws Exception {
        return queueService.createQueue(request).map(response -> ResponseEntity.ok(createSuccessResponse(response)));
    }

}
