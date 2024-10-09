package com.f4.fqs.queue_manage.application.service;

import com.f4.fqs.commons.domain.exception.BusinessException;
import com.f4.fqs.queue_manage.application.response.CreateQueueResponse;
import com.f4.fqs.queue_manage.domain.model.Queue;
import com.f4.fqs.queue_manage.infrastructure.repository.QueueRepository;
import com.f4.fqs.queue_manage.presentation.request.CreateQueueRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Base64;

import static com.f4.fqs.queue_manage.presentation.exception.QueueErrorCode.QUEUE_NAME_DUPLICATE;

@Slf4j
@RequiredArgsConstructor
@Service
public class QueueService {

    private final QueueRepository queueRepository;
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    @Transactional
    public CreateQueueResponse createQueue(CreateQueueRequest request, Long userId) {
        String secretKey = this.generateRandomString();

        if (queueRepository.existsByName(request.name())) {
            throw new BusinessException(QUEUE_NAME_DUPLICATE);
        }

        // TODO. userId 존재 확인?

        Queue queue = Queue.from(request, secretKey, userId);
        Queue savedQueue = queueRepository.save(queue);

        this.queueInfoSaveToRedis(secretKey, savedQueue);

        // TODO. 개인별 인스턴스 생성 (비동기)

        return new CreateQueueResponse(savedQueue.getId(), savedQueue.getSecretKey());
    }

    private String generateRandomString() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[16];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes).substring(0, 16);
    }

    private void queueInfoSaveToRedis(String secretKey, Queue queue) {
        try {
            String json = objectMapper.writeValueAsString(queue);
            redisTemplate.opsForSet().add(secretKey, json); // 존재 확인 - redisTemplate.hasKey(secretKey)
        } catch (Exception e) {
            throw new RuntimeException("Failed to save to Redis", e);
        }
    }
}