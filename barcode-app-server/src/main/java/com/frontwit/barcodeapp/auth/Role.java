package com.frontwit.barcodeapp.auth;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN,
    USER,
    READ_ONLY;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
