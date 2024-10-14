package com.f4.fqs.gateway.handler;

import com.f4.fqs.gateway.application.response.ApiRouteDto;
import com.f4.fqs.gateway.application.service.RouteService;
import com.f4.fqs.gateway.config.GatewayRoutesRefresher;
import com.f4.fqs.gateway.domain.ApiRoute;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@RequiredArgsConstructor
@Component
public class ApiRouteHandler {
    private final RouteService routeService;

    private final GatewayRoutesRefresher gatewayRoutesRefresher;

    public Mono<ServerResponse> create(ServerRequest serverRequest) {
        Mono<ApiRoute> apiRoute = serverRequest.bodyToMono(ApiRoute.class);

        return apiRoute.flatMap(route ->
                ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(routeService.create(route), ApiRouteDto.class));
    }

    public Mono<ServerResponse> getById(ServerRequest serverRequest) {
        final String apiId = serverRequest.pathVariable("routeId");
        Optional<ApiRoute> apiRoute = routeService.getById(apiId);

        return apiRoute.map(route -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(route)) // ApiRoute 반환
                .orElseGet(() -> ServerResponse.notFound().build()); // 존재하지 않을 경우 404 반환
    }

    public Mono<ServerResponse> refreshRoutes(ServerRequest serverRequest) {
        gatewayRoutesRefresher.refreshRoutes();

        return ServerResponse.ok().body(BodyInserters.fromObject("Routes reloaded successfully"));
    }
}
