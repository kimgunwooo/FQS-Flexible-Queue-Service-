// 당장 사용 안 해서 일단 주석 처리
//package com.f4.fqs.gateway.application.service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.concurrent.TimeUnit;
//
//@Service
//@RequiredArgsConstructor
//public class RedisService {
//
//    private final RedisTemplate<String, Object> redisTemplate;
//
//    private final ObjectMapper objectMapper;
//
//    // 값을 redis에 저장하는 메서드
//    public void setValue(String key, Object value) {
//        redisTemplate.opsForValue().set(key, value);
//    }
//
//    // 값과 함께 TTL(만료시간)을 설정해서 저장하는 메서드
//    public void setValueWithExpiry(String key, Object value, long timeout, TimeUnit unit) {
//        redisTemplate.opsForValue().set(key, value, timeout, unit);
//    }
//
//    // redis에서 값을 가져오는 메서드
//    public Object getValue(String key) {
//        return redisTemplate.opsForValue().get(key);
//    }
//
//    // redis에서 값을 가져오는 메서드
//    public <T> T getValueAsClass(String key, Class<T> clazz) {
//        return objectMapper.convertValue(redisTemplate.opsForValue().get(key), clazz);
//    }
//
//    // redis에서 특정 키의 TTL(남은 만료시간)을 조회하는 메서드
//    public Long getExpire(String key) {
//        return redisTemplate.getExpire(key);
//    }
//
//    // 특정 키의 만료시간을 갱신하는 메서드,
//    public Boolean setExpire(String key, long timeout, TimeUnit unit) {
//        return redisTemplate.expire(key, timeout, unit);
//    }
//
//    // redis에 키가 존재하는지 확인하는 메서드
//    public Boolean hasKey(String key) {
//        return redisTemplate.hasKey(key);
//    }
//
//
//}