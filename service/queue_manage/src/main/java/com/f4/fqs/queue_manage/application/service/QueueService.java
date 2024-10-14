package com.f4.fqs.queue_manage.application.service;

import com.f4.fqs.commons.domain.exception.BusinessException;
import com.f4.fqs.gateway_domain.domain.ApiRoute;
import com.f4.fqs.queue_manage.application.response.CloseQueueResponse;
import com.f4.fqs.queue_manage.application.response.CreateQueueResponse;
import com.f4.fqs.queue_manage.application.response.QueueInfo;
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

import static com.f4.fqs.gateway_domain.domain.GatewayConstant.ROUTE_KEY_PREFIX;
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
        validateQueueName(request.name());

        int springPort = portManager.allocateSpringPort();
        int redisPort = portManager.allocateRedisPort();
        String secretKey = generateRandomString();

        Queue savedQueue = saveQueue(request, secretKey, userId, springPort, redisPort);
        this.saveQueueInfoToRedis(secretKey, savedQueue);
        this.addApiRoute(savedQueue);
        this.runDockerService(savedQueue.getName(), springPort, redisPort);

        return new CreateQueueResponse(savedQueue.getId(), savedQueue.getSecretKey());
    }

    private void validateQueueName(String queueName) {
        if (queueRepository.existsByName(queueName)) {
            throw new BusinessException(QUEUE_NAME_DUPLICATE);
        }
    }

    private Queue saveQueue(CreateQueueRequest request, String secretKey, Long userId, int springPort, int redisPort) {
        Queue queue = Queue.from(request, secretKey, userId, springPort, redisPort);
        return queueRepository.save(queue);
    }

    private void saveQueueInfoToRedis(String secretKey, Queue queue) {
        String json = serializeQueueToJson(queue);
        redisTemplate.opsForSet().add(secretKey, json);
    }

    private String serializeQueueToJson(Queue queue) {
        try {
            return objectMapper.writeValueAsString(queue);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize queue to JSON", e);
        }
    }

    private void addApiRoute(Queue savedQueue) {
        ApiRoute apiRoute = new ApiRoute(
                String.format("%s_queue_server", savedQueue.getName()),
                String.format("lb://%s", savedQueue.getName()),
                "/queue/**"
        );
        redisTemplate.opsForValue().set(ROUTE_KEY_PREFIX, apiRoute);
    }

    private void runDockerService(String queueName, int springPort, int redisPort) {
        dockerService.runServices(queueName, springPort, redisPort, "kwforu/hello-controller");
    }

    private String generateRandomString() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[16];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes).substring(0, 16);
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