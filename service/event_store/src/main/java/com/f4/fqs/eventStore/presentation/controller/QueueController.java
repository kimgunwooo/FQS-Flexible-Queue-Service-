package com.f4.fqs.eventStore.presentation.controller;

import com.f4.fqs.eventStore.application.service.QueueService;
import com.f4.fqs.eventStore.presentation.request.EventRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static com.f4.fqs.commons.domain.response.ResponseUtil.createSuccessResponse;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/store")
public class QueueController {

    private final QueueService queueService;

//    @PostMapping
//    public Mono<ResponseEntity<Void>> event(@RequestBody EventRequest request) {
//        return queueService.event(request).map(response -> ResponseEntity.ok(createSuccessResponse(response)));
//    }

}
