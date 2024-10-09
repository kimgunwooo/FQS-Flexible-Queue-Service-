package com.f4.fqs.auth.jwt;

import java.util.Collection;
import java.util.Collections;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class JwtAuthentication implements Authentication {

    @Getter
    private final Long userId;
    @Getter
    private final String email;
    @Getter
    private final String role;
    private boolean authenticated;

    public JwtAuthentication(Long userId, String email, String role) {
        this.userId = userId;
        this.email = email;
        this.role = role;
        this.authenticated = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this; // JwtAuthentication 객체를 반환
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return String.valueOf(userId);
    }

    public Long customUserId(){
        return userId;
    }
}
