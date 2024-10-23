package com.f4.fqs.queue.redis.service;

import com.f4.fqs.commons.domain.exception.BusinessException;
import com.f4.fqs.queue.presentation.exception.QueueErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

import static com.f4.fqs.commons.domain.util.CommonConstraints.QUEUE_NAME;


@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {

    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;


    public Mono<Boolean> lineUp(UUID userId) {

        final LocalDateTime now = LocalDateTime.now();

        return reactiveRedisTemplate.opsForZSet()
                .add(QUEUE_NAME, userId.toString(), now.toEpochSecond(ZoneOffset.UTC) + (now.getNano() / 1000000000.0));

    }

    public Flux<String> consume(int size) {

        return reactiveRedisTemplate.opsForZSet()
                .popMin(QUEUE_NAME, size)
                .mapNotNull(ZSetOperations.TypedTuple::getValue);

    }

    public Mono<Long> getMyRank(String userId) {

        return reactiveRedisTemplate.opsForZSet().rank(QUEUE_NAME, userId)
                .onErrorMap((e) -> new BusinessException(QueueErrorCode.NOT_EXIST_WAITING_INFO));

    }

}