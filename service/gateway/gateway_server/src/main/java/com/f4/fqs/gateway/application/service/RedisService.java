// 당장 사용 안 해서 일단 주석 처리
package com.f4.fqs.gateway.application.service;

import com.f4.fqs.gateway_domain.domain.ApiRoute;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final ReactiveRedisTemplate<String, ApiRoute> redisTemplate;

    private final ObjectMapper objectMapper;

    // 값을 redis에 저장하는 메서드
    public void setValue(String key, ApiRoute value) {
        redisTemplate.opsForValue().set(key, value);
    }


    // redis에서 값을 가져오는 메서드
    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // redis에서 값을 가져오는 메서드
    public <T> T getValueAsClass(String key, Class<T> clazz) {
        return objectMapper.convertValue(redisTemplate.opsForValue().get(key), clazz);
    }



    // redis에 키가 존재하는지 확인하는 메서드
//    public Boolean hasKey(String key) {
//        return redisTemplate.hasKey(key);
//    }


}