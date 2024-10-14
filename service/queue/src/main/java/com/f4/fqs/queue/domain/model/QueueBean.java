package com.f4.fqs.queue.domain.model;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Table("queue_bean")
public class QueueBean {
    @Id
    private Long id;

    private String name;

    private boolean messageOrderGuaranteed;

    private boolean messageDuplicationAllowed;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder(access = AccessLevel.PROTECTED)
    public QueueBean(String name, boolean messageOrderGuaranteed, boolean messageDuplicationAllowed) {
        this.name = name;
        this.messageOrderGuaranteed = messageOrderGuaranteed;
        this.messageDuplicationAllowed = messageDuplicationAllowed;
    }
}
