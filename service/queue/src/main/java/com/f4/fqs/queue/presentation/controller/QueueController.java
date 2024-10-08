package com.f4.fqs.queue.presentation.controller;

import com.f4.fqs.commons.domain.response.ResponseBody;
import com.f4.fqs.queue.application.response.CreateQueueResponse;
import com.f4.fqs.queue.application.service.QueueService;
import com.f4.fqs.queue.infrastructure.aop.AuthorizationRequired;
import com.f4.fqs.queue.presentation.request.CreateQueueRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.f4.fqs.commons.domain.response.ResponseUtil.createSuccessResponse;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/queue")
public class QueueController {

    private final QueueService queueService;

    @AuthorizationRequired({"ROLE_ROOT"}) // TODO. ROOT 권한의 이름으로 대체
    @PostMapping
    public Mono<ResponseEntity<ResponseBody<CreateQueueResponse>>> createQueue(
            @RequestBody @Valid CreateQueueRequest request,
            @RequestHeader("X-User-Id") Long userId,
            ServerWebExchange exchange) {
        return queueService.createQueue(request, userId).map(response -> ResponseEntity.ok(createSuccessResponse(response)));
    }

}
