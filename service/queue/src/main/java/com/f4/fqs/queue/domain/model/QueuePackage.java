package com.f4.fqs.queue.domain.model;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Table("queue_package")
public class QueuePackage {
    @Id
    private Long id;

    private Long producerId;

    private Long consumerId;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder(access = AccessLevel.PROTECTED)
    public QueuePackage(Long producerId, Long consumerId) {
        this.producerId = producerId;
        this.consumerId = consumerId;
    }
}
