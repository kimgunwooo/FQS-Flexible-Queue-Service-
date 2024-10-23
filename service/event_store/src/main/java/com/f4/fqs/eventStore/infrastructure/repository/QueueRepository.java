package com.f4.fqs.eventStore.infrastructure.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Queue;

@Repository
public interface QueueRepository extends ReactiveCrudRepository<Queue, Long> {
    Mono<Boolean> existsByName(String name);
}
