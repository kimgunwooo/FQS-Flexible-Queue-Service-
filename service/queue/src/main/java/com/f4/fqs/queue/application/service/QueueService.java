package com.f4.fqs.queue.application.service;

import com.f4.fqs.commons.domain.exception.BusinessException;
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

import static com.f4.fqs.queue.presentation.exception.QueueErrorCode.QUEUE_NAME_DUPLICATE;

@Slf4j
@RequiredArgsConstructor
@Service
public class QueueService {

    private final QueueRepository queueRepository;
    private final QueuePackageService packageService;

    @Transactional
    public Mono<CreateQueueResponse> createQueue(CreateQueueRequest request, Long userId) {
        String secretKey = generateRandomString();

        return queueRepository.existsByName(request.name())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new BusinessException(QUEUE_NAME_DUPLICATE));
                    }

                    return packageService.validateQueuePackage(request.queuePackageId())
                            .flatMap(queuePackage -> {
                                Queue queue = Queue.from(request, secretKey, userId, queuePackage.getId());
                                return queueRepository.save(queue)
                                        .map(q -> new CreateQueueResponse(q.getSecretKey()));
                            });
                });
    }

    private String generateRandomString() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[16];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes).substring(0, 16);
    }
}
