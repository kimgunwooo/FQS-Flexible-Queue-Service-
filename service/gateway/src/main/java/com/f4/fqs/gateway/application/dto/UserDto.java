package com.f4.fqs.gateway.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private Long userId;
    private String username;
    private Collection<String> roles;

}