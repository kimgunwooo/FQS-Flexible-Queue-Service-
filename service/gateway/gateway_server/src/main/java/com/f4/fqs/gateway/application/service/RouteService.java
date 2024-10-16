package com.f4.fqs.gateway.application.service;

import com.f4.fqs.gateway_domain.domain.ApiRoute;
import reactor.core.publisher.Flux;

public interface RouteService {
    Flux<ApiRoute> getAll();
}