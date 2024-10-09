package com.f4.fqs.queue_manage.presentation.controller;

import com.f4.fqs.commons.domain.response.ResponseBody;
import com.f4.fqs.queue_manage.application.response.CreateQueueResponse;
import com.f4.fqs.queue_manage.application.service.QueueService;
import com.f4.fqs.queue_manage.infrastructure.aop.AuthorizationRequired;
import com.f4.fqs.queue_manage.presentation.request.CreateQueueRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.f4.fqs.commons.domain.response.ResponseUtil.createSuccessResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/queue")
public class QueueController {

    private final QueueService queueService;

    @AuthorizationRequired({"ROLE_ROOT"}) // TODO. ROOT 권한의 이름으로 대체
    @PostMapping
    public ResponseEntity<ResponseBody<CreateQueueResponse>> createQueue(
            @RequestBody @Valid CreateQueueRequest request,
            @RequestHeader("X-User-Id") Long userId,
            HttpServletRequest httpRequest) {
        return ResponseEntity.ok(createSuccessResponse(queueService.createQueue(request, userId)));
    }
}
