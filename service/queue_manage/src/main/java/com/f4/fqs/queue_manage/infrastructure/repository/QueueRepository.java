package com.f4.fqs.queue_manage.infrastructure.repository;

import com.f4.fqs.queue_manage.domain.model.Queue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QueueRepository extends JpaRepository<Queue, Long> {
    boolean existsByName(String name);

    List<Queue> findByUserId(Long userId);
}
