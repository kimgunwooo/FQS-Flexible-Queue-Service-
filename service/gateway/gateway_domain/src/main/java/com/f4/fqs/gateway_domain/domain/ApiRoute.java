package com.f4.fqs.gateway_domain.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiRoute {
    private String routeIdentifier;
    private String uri;
    private String path;
}