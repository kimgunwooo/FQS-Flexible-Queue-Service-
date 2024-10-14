package com.f4.fqs.queue.infrastructure.repository;

import com.f4.fqs.queue.domain.model.QueueBean;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueueBeanRepository extends ReactiveCrudRepository<QueueBean, Long> {
}
