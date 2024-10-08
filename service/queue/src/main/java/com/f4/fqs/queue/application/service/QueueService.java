package com.f4.fqs.queue.application.service;

import com.f4.fqs.commons.domain.exception.BusinessException;
import com.f4.fqs.queue.application.response.CreateQueueResponse;
import com.f4.fqs.queue.application.response.QueueBeanInfo;
import com.f4.fqs.queue.domain.model.Queue;
import com.f4.fqs.queue.domain.model.QueuePackage;
import com.f4.fqs.queue.infrastructure.repository.QueueRepository;
import com.f4.fqs.queue.presentation.request.CreateQueueRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.security.SecureRandom;
import java.util.Base64;

import static com.f4.fqs.queue.presentation.exception.QueueErrorCode.QUEUE_NAME_DUPLICATE;

@Slf4j
@RequiredArgsConstructor
@Service
public class QueueService {

    private final QueueRepository queueRepository;
    private final QueuePackageService packageService;
    private final QueueBeanService queueBeanService;
    private final ObjectMapper objectMapper;
    private final ReactiveRedisTemplate<String, Object> reactiveRedisTemplate;

    @Transactional
    public Mono<CreateQueueResponse> createQueue(CreateQueueRequest request, Long userId) {
        String secretKey = generateRandomString();

        return queueRepository.existsByName(request.name())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new BusinessException(QUEUE_NAME_DUPLICATE));
                    }
                    return packageService.validateQueuePackage(request.queuePackageId())
                            .flatMap(queuePackage -> createAndSaveQueue(request, userId, secretKey, queuePackage));
                });
    }

    private Mono<CreateQueueResponse> createAndSaveQueue(CreateQueueRequest request, Long userId, String secretKey, QueuePackage queuePackage) {
        Queue queue = Queue.from(request, secretKey, userId, queuePackage.getId());
        return queueRepository.save(queue)
                .flatMap(q -> Mono.zip(
                                queueBeanService.getBeanName(queuePackage.getProducerId()),
                                queueBeanService.getBeanName(queuePackage.getConsumerId())
                        )
                        .flatMap(tuple -> saveToRedis(secretKey, tuple.getT1(), tuple.getT2())
                                .then(Mono.just(new CreateQueueResponse(q.getSecretKey())))));
    }

    private Mono<Void> saveToRedis(String secretKey, String producerBeanName, String consumerBeanName) {
        QueueBeanInfo queueBeanInfo = new QueueBeanInfo(producerBeanName, consumerBeanName);
        // TODO. 종료 시간의 단위가 정해지면 TTL 설정
        return Mono.fromCallable(() -> objectMapper.writeValueAsString(queueBeanInfo))
                .flatMap(json -> reactiveRedisTemplate.opsForValue().set(secretKey, json))
                .then();
    }

    private String generateRandomString() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[16];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes).substring(0, 16);
    }
}
