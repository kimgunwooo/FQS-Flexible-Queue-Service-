package com.f4.fqs.queue.application.service;

import com.f4.fqs.commons.domain.exception.BusinessException;
import com.f4.fqs.queue.domain.model.QueuePackage;
import com.f4.fqs.queue.infrastructure.repository.QueuePackageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.f4.fqs.queue.presentation.exception.QueueErrorCode.QUEUE_PACKAGE_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Service
public class QueuePackageService {

    private final QueuePackageRepository queuePackageRepository;

    public Mono<QueuePackage> validateQueuePackage(Long queuePackageId) {
        return queuePackageRepository.findById(queuePackageId)
                .switchIfEmpty(Mono.error(new BusinessException(QUEUE_PACKAGE_NOT_FOUND)));
    }
}
