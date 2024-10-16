package com.f4.fqs.queue.infrastructure.repository;

import com.f4.fqs.eventStore.infrastructure.repository.QueueRepository;
import com.f4.fqs.eventStore.presentation.request.EventRequest;
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

}