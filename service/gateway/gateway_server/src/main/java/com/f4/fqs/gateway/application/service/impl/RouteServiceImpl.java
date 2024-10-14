package com.f4.fqs.gateway.application.service.impl;

import com.f4.fqs.gateway.application.service.RouteService;
import com.f4.fqs.gateway_domain.domain.ApiRoute;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import static com.f4.fqs.gateway_domain.domain.GatewayConstant.ROUTE_KEY_PREFIX;

@RequiredArgsConstructor
@Service
public class RouteServiceImpl implements RouteService {
    private final ReactiveRedisTemplate<String, ApiRoute> reactiveRedisTemplate;

    @Override
    public Flux<ApiRoute> getAll() {
        // 모든 ApiRoute를 가져오기 위해 키를 생성하고 Flux로 반환
        return reactiveRedisTemplate.keys(ROUTE_KEY_PREFIX)
                .flatMap(key -> reactiveRedisTemplate.opsForValue().get(key));
    }
}
