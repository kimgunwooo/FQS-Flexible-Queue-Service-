package com.f4.fqs.gateway.application.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiRouteDto {

    private String id;
    private String routeIdentifier;
    private String uri;
    private String method;
    private String path;
}
