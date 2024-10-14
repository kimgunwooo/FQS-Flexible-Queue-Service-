package com.f4.fqs.queue.application.service;

import com.f4.fqs.commons.domain.exception.BusinessException;
import com.f4.fqs.commons.domain.util.ParsingUtil;
import com.f4.fqs.queue.application.response.AddQueueResponse;
import com.f4.fqs.queue.application.response.ConsumeQueueResponse;
import com.f4.fqs.queue.application.response.FindRankResponse;
import com.f4.fqs.queue.infrastructure.exception.QueueErrorCode;
import com.f4.fqs.queue.presentation.request.AddQueueRequest;
import com.f4.fqs.queue.presentation.request.FindRankRequest;
import com.f4.fqs.queue.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Service
public class QueueService {

    private final RedisService redisService;

    public Mono<AddQueueResponse> lineUp() {

        UUID uuid = UUID.randomUUID();

        CompletableFuture.runAsync(() -> redisService.lineUp(uuid));

        /**
         * TODO EventSourcing :: ADD_QUEUE
         */

        return Mono.just(new AddQueueResponse(uuid.toString()));

    }

    public Mono<ConsumeQueueResponse> consume(int size) {

        if(size <= 0) {
            return Mono.error(new BusinessException(QueueErrorCode.CONSUME_SIZE_MUST_BE_OVER_ZERO));
        }

        List<String> list = redisService.consume(size);

        ConsumeQueueResponse result = new ConsumeQueueResponse(list);

        /**
         * TODO EventSourcing :: CONSUME_QUEUE
         */

        return Mono.just(result);

    }

    public Mono<FindRankResponse> getCurrentOrder(FindRankRequest request) {

//        long myRank = redisService.getMyRank(ParsingUtil.makeJsonString(request.userId()));
        long myRank = redisService.getMyRank(request.userId().toString());

        return Mono.just(new FindRankResponse(myRank));
    }
}
