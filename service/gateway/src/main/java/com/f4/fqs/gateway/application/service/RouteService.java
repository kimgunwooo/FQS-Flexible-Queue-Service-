package com.f4.fqs.gateway.application.service;

import com.f4.fqs.gateway.application.response.ApiRouteDto;
import com.f4.fqs.gateway.domain.ApiRoute;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

public interface RouteService {
    List<ApiRoute> getAll();

    ApiRoute create(ApiRoute apiRoute);

    Optional<ApiRoute> getById(String id);
}