package com.frontwit.barcodeapp.auth;

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
        return this.type;
    }

    final class Type {
        public static final String ADMIN = "ADMIN";
        public static final String USER = "USER";

        private Type() {
        }
    }
}

