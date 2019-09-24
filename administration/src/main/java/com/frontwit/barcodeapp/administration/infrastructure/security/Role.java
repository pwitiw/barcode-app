package com.frontwit.barcodeapp.administration.infrastructure.security;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN(Type.ADMIN),
    USER(Type.USER);

    private String type;

    Role(String type) {
        this.type = type;
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + this.type;
    }

    public final class Type {
         static final String ADMIN = "ADMIN";
         static final String USER = "USER";

        private Type() {
        }
    }
}

