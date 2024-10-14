package com.f4.fqs.gateway.infrastructure.repository;

import com.f4.fqs.gateway.application.response.ApiRouteDto;
import com.f4.fqs.gateway.domain.ApiRoute;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends CrudRepository<ApiRoute, String> {
}
