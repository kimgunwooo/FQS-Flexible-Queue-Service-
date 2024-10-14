package com.f4.fqs.queue.infrastructure.repository;

import com.f4.fqs.queue.domain.model.Queue;
import com.f4.fqs.queue.presentation.request.CreateQueueRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataR2dbcTest
@ActiveProfiles("test")
class QueueRepositoryTest {

    @Autowired
    private QueueRepository queueRepository;

    private CreateQueueRequest testRequest;

    @BeforeEach
    void setUp() {
        testRequest = new CreateQueueRequest(
                "TestQueue",
                3600,
                1024,
                60,
                true,
                false
        );
    }

    @Test
    void testSaveQueue() {
        // Given
        String secretKey = generateRandomString(16);
        Queue queue = Queue.from(testRequest, secretKey, 1L, 1L);

        // When
        Mono<Queue> savedQueueMono = queueRepository.save(queue);

        // Then
        StepVerifier.create(savedQueueMono)
                .expectNextMatches(savedQueue -> {
                    assertThat(savedQueue.getId()).isNotNull(); // ID는 자동 생성되므로 null이 아님
                    assertThat(savedQueue.getName()).isEqualTo("TestQueue");
                    assertThat(savedQueue.getMessageRetentionPeriod()).isEqualTo(3600);
                    assertThat(savedQueue.getMaxMessageSize()).isEqualTo(1024);
                    assertThat(savedQueue.getExpirationTime()).isEqualTo(60);
                    assertThat(savedQueue.isMessageOrderGuaranteed()).isTrue();
                    assertThat(savedQueue.isMessageDuplicationAllowed()).isFalse();
                    assertThat(savedQueue.getSecretKey()).isEqualTo(secretKey);
                    return true;
                })
                .verifyComplete();
    }


    private String generateRandomString(int length) {
        return java.util.UUID.randomUUID().toString().substring(0, length);
    }
}