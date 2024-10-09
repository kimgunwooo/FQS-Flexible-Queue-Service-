package com.f4.fqs.queue_manage.domain.model;

import com.f4.fqs.queue_manage.presentation.request.CreateQueueRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "queues")
@SQLDelete(sql = "UPDATE queues SET deleted_at = NOW() where id=?")
@SQLRestriction(value = "deleted_at is NULL")
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
    private int expirationTime;

    private boolean messageOrderGuaranteed;

    private boolean messageDuplicationAllowed;

    @Column(nullable = false, unique = true)
    private String secretKey;

    @Builder(access = AccessLevel.PROTECTED)
    public Queue(int expirationTime, int maxMessageSize, boolean messageDuplicationAllowed, boolean messageOrderGuaranteed, int messageRetentionPeriod, String name, String secretKey, Long userId) {
        this.expirationTime = expirationTime;
        this.maxMessageSize = maxMessageSize;
        this.messageDuplicationAllowed = messageDuplicationAllowed;
        this.messageOrderGuaranteed = messageOrderGuaranteed;
        this.messageRetentionPeriod = messageRetentionPeriod;
        this.name = name;
        this.secretKey = secretKey;
        this.userId = userId;
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
}
