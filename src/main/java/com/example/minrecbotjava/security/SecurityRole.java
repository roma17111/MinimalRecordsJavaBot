package com.example.minrecbotjava.security;

import org.springframework.security.core.GrantedAuthority;

public enum SecurityRole implements GrantedAuthority {

    USER,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
