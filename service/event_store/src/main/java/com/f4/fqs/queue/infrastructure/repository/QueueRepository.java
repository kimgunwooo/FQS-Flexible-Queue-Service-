package com.f4.fqs.queue.infrastructure.repository;

import com.f4.fqs.queue.domain.model.Queue;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface QueueRepository extends ReactiveCrudRepository<Queue, Long> {
    Mono<Boolean> existsByName(String name);
}
