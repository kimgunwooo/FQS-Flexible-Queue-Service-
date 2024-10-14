package com.f4.fqs.queue_manage.application.service;

import com.f4.fqs.commons.domain.exception.BusinessException;
import com.f4.fqs.queue_manage.application.response.CloseQueueResponse;
import com.f4.fqs.queue_manage.application.response.CreateQueueResponse;
import com.f4.fqs.queue_manage.application.response.UpdateQueueExpirationTimeResponse;
import com.f4.fqs.queue_manage.domain.model.Queue;
import com.f4.fqs.queue_manage.infrastructure.docker.DockerService;
import com.f4.fqs.queue_manage.infrastructure.repository.QueueRepository;
import com.f4.fqs.queue_manage.presentation.request.CreateQueueRequest;
import com.f4.fqs.queue_manage.presentation.request.UpdateExpirationTimeRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

import static com.f4.fqs.queue_manage.presentation.exception.QueueErrorCode.*;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class QueueService {

    private final QueueRepository queueRepository;
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final DockerService dockerService;
    private final PortManager portManager;

    @Transactional
    public CreateQueueResponse createQueue(CreateQueueRequest request, Long userId) {
        String secretKey = this.generateRandomString();

        if (queueRepository.existsByName(request.name())) {
            throw new BusinessException(QUEUE_NAME_DUPLICATE);
        }

        // TODO. userId 존재 확인?

        int springPort = portManager.allocateSpringPort();
        int redisPort = portManager.allocateRedisPort();

        Queue queue = Queue.from(request, secretKey, userId, springPort, redisPort);
        Queue savedQueue = queueRepository.save(queue);

        this.queueInfoSaveToRedis(secretKey, savedQueue);

        // TODO. 이미지를 큐 서버로 변경하기 + (추가기능)이미지를 옵션값에 따라 동적으로 수정하기.
        dockerService.runServices(savedQueue.getName(), springPort, redisPort, "kwforu/hello-controller");

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

    public List<QueueInfo> getQueueInfoByUserId(Long userId) {
        List<Queue> queues = queueRepository.findByUserId(userId);
        return queues.stream()
                .map(QueueInfo::fromQueue)
                .toList();
    }

    @Transactional
    public UpdateQueueExpirationTimeResponse updateExpirationTime(Long queueId, Long userId, UpdateExpirationTimeRequest request) {
        Queue queue = this.validateQueueOwnership(queueId, userId);
        queue.updateExpirationTime(request.expirationTime());
        return new UpdateQueueExpirationTimeResponse(queue.getId(), queue.getExpirationTime());
    }

    @Transactional
    public CloseQueueResponse closeQueue(Long queueId, Long userId) {
        Queue queue = this.validateQueueOwnership(queueId, userId);
        queue.closeQueue(false, LocalDateTime.now());
        // TODO. 실제 서버 인스턴스 종료 로직
        dockerService.stopServices(queue.getName());
        portManager.releaseSpringPort(queue.getSpringPort());
        portManager.releaseRedisPort(queue.getRedisPort());
        return new CloseQueueResponse(queue.getId(), queue.getExpirationTime());
    }

    private Queue validateQueueOwnership(Long queueId, Long userId) {
        Queue queue = queueRepository.findById(queueId)
                .orElseThrow(() -> new BusinessException(QUEUE_NOT_FOUND));

        if (!queue.getUserId().equals(userId)) {
            throw new BusinessException(QUEUE_USER_NOT_MATCHED);
        }

        return queue;
    }
}