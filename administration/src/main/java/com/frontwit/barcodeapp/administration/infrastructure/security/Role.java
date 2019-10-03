package com.frontwit.barcodeapp.administration.infrastructure.security;

import org.springframework.security.core.GrantedAuthority;

enum Role implements GrantedAuthority {
    ADMIN, USER;

    @Override
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}

