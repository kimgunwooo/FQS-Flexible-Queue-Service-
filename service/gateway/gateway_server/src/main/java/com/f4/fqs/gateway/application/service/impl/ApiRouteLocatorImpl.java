package com.f4.fqs.gateway.application.service.impl;

import com.f4.fqs.gateway.application.service.RouteService;
import com.f4.fqs.gateway_domain.domain.ApiRoute;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.BooleanSpec;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class ApiRouteLocatorImpl implements RouteLocator {
    private final RouteLocatorBuilder routeLocatorBuilder;
    private final RouteService routeService;

    @Override
    public Flux<Route> getRoutes() {
        RouteLocatorBuilder.Builder routesBuilder = routeLocatorBuilder.routes();

        // routeService.getAll()을 Flux<ApiRoute>로 변경
        return routeService.getAll() // Flux<ApiRoute> 반환
                .map(apiRoute -> routesBuilder.route(String.valueOf(apiRoute.getRouteIdentifier()),
                        predicateSpec -> setPredicateSpec(apiRoute, predicateSpec)))
                .collectList()
                .flatMapMany(builders -> routesBuilder.build().getRoutes());
    }

    private Buildable<Route> setPredicateSpec(ApiRoute apiRoute, PredicateSpec predicateSpec) {
        return predicateSpec.path(apiRoute.getPath())
                .uri(apiRoute.getUri()); // 모든 메서드 허용
    }

    @Override
    public Flux<Route> getRoutesByMetadata(Map<String, Object> metadata) {
        return RouteLocator.super.getRoutesByMetadata(metadata);
    }
}