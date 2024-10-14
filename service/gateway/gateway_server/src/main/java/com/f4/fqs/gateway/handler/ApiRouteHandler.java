package com.f4.fqs.gateway.handler;

import com.f4.fqs.gateway.application.service.RouteService;
import com.f4.fqs.gateway.config.GatewayRoutesRefresher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class ApiRouteHandler {
    private final RouteService routeService;
    private final GatewayRoutesRefresher gatewayRoutesRefresher;

    public Mono<ServerResponse> refreshRoutes(ServerRequest serverRequest) {
        gatewayRoutesRefresher.refreshRoutes();

        return ServerResponse.ok().body(BodyInserters.fromObject("Routes reloaded successfully"));
    }
}
