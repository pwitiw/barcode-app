package com.frontwit.barcodeapp.administration.infrastructure.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Service
public class JwtTokenUtil {

    private static final long JWT_TOKEN_VALIDITY = 860_400_000L; // 10 days
    private static final String TOKEN_PREFIX = "Bearer ";

    @Value("${jwt.secret}")
    private String secret;

    String createToken(UserDetails details) {
        String[] roles = details.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new);

        String token = JWT.create()
                .withSubject(details.getUsername())
                .withArrayClaim("role", roles)
                .withExpiresAt(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .sign(getAlgorithm());

        return TOKEN_PREFIX + token;
    }

    String getSubject(String token) {
        return JWT.require(getAlgorithm())
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""))
                .getSubject();
    }

    void validate(String token) {
        var expireDate = JWT.require(getAlgorithm())
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""))
                .getExpiresAt();
        if (new Date().compareTo(expireDate) > 0) {
            throw new IllegalStateException("Invalid token");
        }
    }

    private Algorithm getAlgorithm() {
        return HMAC512(secret.getBytes());
    }
}
