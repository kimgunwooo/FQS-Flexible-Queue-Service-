package com.f4.fqs.queue.application.service;

import com.f4.fqs.queue.application.response.CreateQueueResponse;
import com.f4.fqs.queue.domain.model.Queue;
import com.f4.fqs.queue.infrastructure.repository.QueueRepository;
import com.f4.fqs.queue.presentation.request.CreateQueueRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.security.SecureRandom;
import java.util.Base64;

@Slf4j
@RequiredArgsConstructor
@Service
public class QueueService {

    private final QueueRepository queueRepository;

    @Transactional
    public Mono<CreateQueueResponse> createQueue(CreateQueueRequest request) {
        String secretKey = generateRandomString(16);
        Queue queue = Queue.from(request, secretKey);

        return queueRepository.save(queue)
                .map(q -> new CreateQueueResponse(q.getSecretKey()))
                .doOnError(e -> log.error("큐 저장 중 오류 발생: {}", e.getMessage()));
    }


    private String generateRandomString(int length) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[length];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes).substring(0, length);
    }
}
