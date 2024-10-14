package com.f4.fqs.gateway.application.service.impl;

import com.f4.fqs.gateway.application.response.ApiRouteDto;
import com.f4.fqs.gateway.application.service.RouteService;
import com.f4.fqs.gateway.domain.ApiRoute;
import com.f4.fqs.gateway.infrastructure.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class RouteServiceImpl implements RouteService {
    private final RouteRepository routeRepository;

    public RouteServiceImpl(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @Override
    public List<ApiRoute> getAll() {
        return (List<ApiRoute>) this.routeRepository.findAll();
    }

    @Override
    public ApiRoute create(ApiRoute apiRoute) {
        return this.routeRepository.save(apiRoute);
    }

    @Override
    public Optional<ApiRoute> getById(String id) {
        return this.routeRepository.findById(id);
    }
}