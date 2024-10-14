package com.f4.fqs.queue.application.service;

import com.f4.fqs.commons.domain.exception.BusinessException;
import com.f4.fqs.queue.domain.model.QueueBean;
import com.f4.fqs.queue.infrastructure.repository.QueueBeanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.f4.fqs.queue.presentation.exception.QueueErrorCode.QUEUE_BEAN_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Service
public class QueueBeanService {

    private final QueueBeanRepository queueBeanRepository;

    public Mono<String> getBeanName(Long beanId) {
        return queueBeanRepository.findById(beanId)
                .switchIfEmpty(Mono.error(new BusinessException(QUEUE_BEAN_NOT_FOUND)))
                .map(QueueBean::getName);
    }

}
