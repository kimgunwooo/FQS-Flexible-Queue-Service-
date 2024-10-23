package com.f4.fqs.queue_manage.application.service;

import com.f4.fqs.commons.domain.exception.BusinessException;
import jakarta.annotation.PostConstruct;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import static com.f4.fqs.queue_manage.presentation.exception.QueueErrorCode.QUEUE_PORT_NOT_EXITED;

@RequiredArgsConstructor
@Service
public class PortManager {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String SPRING_PORTS_KEY = "available_spring_ports";
    private static final String REDIS_PORTS_KEY = "available_redis_ports";
    private static final int NUMBER_OF_PORTS = 11; // 11개 open

    @PostConstruct
    public void initializeAvailablePorts() {
        for (int port = 8080; port < 8080 + NUMBER_OF_PORTS; port++) {
            redisTemplate.opsForSet().add(SPRING_PORTS_KEY, port);
        }

        for (int port = 6380; port < 6380 + NUMBER_OF_PORTS; port++) {
            redisTemplate.opsForSet().add(REDIS_PORTS_KEY, port);
        }
    }

    public int allocateSpringPort() {
        return allocatePort(SPRING_PORTS_KEY);
    }

    public int allocateRedisPort() {
        return allocatePort(REDIS_PORTS_KEY);
    }

    private int allocatePort(String key) {
        Integer port = (Integer) redisTemplate.opsForSet().pop(key);
        if (port == null) {
            throw new BusinessException(QUEUE_PORT_NOT_EXITED);
        }
        return port;
    }

    public void releaseSpringPort(int port) {
        redisTemplate.opsForSet().add(SPRING_PORTS_KEY, port);
    }

    public void releaseRedisPort(int port) {
        redisTemplate.opsForSet().add(REDIS_PORTS_KEY, port);
    }
}
