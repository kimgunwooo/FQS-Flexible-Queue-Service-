package com.f4.fqs.gateway_domain.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiRoute {
    private String routeIdentifier;
    private String uri;
    private String method;
    private String path;
}