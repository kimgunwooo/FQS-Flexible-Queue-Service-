package com.f4.fqs.gateway_domain.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiRoute implements Serializable {
    private String routeIdentifier;
    private String uri;
    private String method;
    private String path;
}