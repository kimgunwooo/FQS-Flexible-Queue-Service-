package com.f4.fqs.queue_manage.domain.model;

import com.f4.fqs.queue_manage.presentation.request.CreateQueueRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "queues")
public class Queue extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "queue_id")
    private Long Id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String name;

    // TODO. 아래 3개 필드의 단위를 어떻게 가져갈지?
    @Column(nullable = false)
    private int messageRetentionPeriod;

    @Column(nullable = false)
    private int maxMessageSize;

    @Column(nullable = false)
    private LocalDateTime expirationTime;

    @Column(nullable = false)
    private boolean messageOrderGuaranteed;

    @Column(nullable = false)
    private boolean messageDuplicationAllowed;

    @Column(nullable = false, unique = true)
    private String secretKey;

    @Column(nullable = false)
    private Boolean isActive;

    @Builder(access = AccessLevel.PROTECTED)
    public Queue(LocalDateTime expirationTime, int maxMessageSize, boolean messageDuplicationAllowed, boolean messageOrderGuaranteed, int messageRetentionPeriod, String name, String secretKey, Long userId) {
        this.expirationTime = expirationTime;
        this.maxMessageSize = maxMessageSize;
        this.messageDuplicationAllowed = messageDuplicationAllowed;
        this.messageOrderGuaranteed = messageOrderGuaranteed;
        this.messageRetentionPeriod = messageRetentionPeriod;
        this.name = name;
        this.secretKey = secretKey;
        this.userId = userId;
        this.isActive = true;
    }

    public static Queue from(CreateQueueRequest request, String secretKey, Long userId) {
        return Queue.builder()
                .expirationTime(request.expirationTime())
                .maxMessageSize(request.maxMessageSize())
                .messageDuplicationAllowed(request.messageDuplicationAllowed())
                .messageOrderGuaranteed(request.messageOrderGuaranteed())
                .messageRetentionPeriod(request.messageRetentionPeriod())
                .name(request.name())
                .secretKey(secretKey)
                .userId(userId)
                .build();
    }

    public void updateExpirationTime(LocalDateTime localDateTime) {
        this.expirationTime = localDateTime;
    }

    public void closeQueue(boolean isActive, LocalDateTime now) {
        this.isActive = isActive;
        this.expirationTime = now;
    }
}
