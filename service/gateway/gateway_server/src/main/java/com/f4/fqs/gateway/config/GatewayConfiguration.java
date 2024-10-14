package com.f4.fqs.gateway.config;

import com.f4.fqs.gateway.application.service.RouteService;
import com.f4.fqs.gateway.application.service.impl.ApiRouteLocatorImpl;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {

    @Bean
    public RouteLocator routeLocator(RouteService routeService, RouteLocatorBuilder routeLocatorBuilder) {
        return new ApiRouteLocatorImpl(routeLocatorBuilder, routeService);
    }
}