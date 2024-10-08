package com.f4.fqs.queue.domain.model;

import com.f4.fqs.queue.presentation.request.CreateQueueRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Table("queue")
public class Queue {
    @Id
    private Long Id;

    private Long userId;

    private Long queuePackageId;

    private String name;

    // TODO. 아래 3개 필드의 단위를 어떻게 가져갈지?
    private int messageRetentionPeriod;

    private int maxMessageSize;

    private int expirationTime;

    private boolean messageOrderGuaranteed;

    private boolean messageDuplicationAllowed;

    private String secretKey;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder(access = AccessLevel.PROTECTED)
    public Queue(int expirationTime, int maxMessageSize, boolean messageDuplicationAllowed, boolean messageOrderGuaranteed, int messageRetentionPeriod, String name, String secretKey, Long userId, Long queuePackageId) {
        this.expirationTime = expirationTime;
        this.maxMessageSize = maxMessageSize;
        this.messageDuplicationAllowed = messageDuplicationAllowed;
        this.messageOrderGuaranteed = messageOrderGuaranteed;
        this.messageRetentionPeriod = messageRetentionPeriod;
        this.name = name;
        this.secretKey = secretKey;
        this.userId = userId;
        this.queuePackageId = queuePackageId;
    }

    public static Queue from(CreateQueueRequest request, String secretKey, Long userId, Long queuePackageId) {
        return Queue.builder()
                .expirationTime(request.expirationTime())
                .maxMessageSize(request.maxMessageSize())
                .messageDuplicationAllowed(request.messageDuplicationAllowed())
                .messageOrderGuaranteed(request.messageOrderGuaranteed())
                .messageRetentionPeriod(request.messageRetentionPeriod())
                .name(request.name())
                .secretKey(secretKey)
                .userId(userId)
                .queuePackageId(queuePackageId)
                .build();
    }
}
