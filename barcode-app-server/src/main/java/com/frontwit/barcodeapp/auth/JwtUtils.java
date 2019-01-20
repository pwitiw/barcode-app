package com.frontwit.barcodeapp.auth;


import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Service
public class JwtUtils {

    private static final String SECRET = "YmFyY29kZUFwcA==";
    private static final long EXPIRATION_TIME = 860_400_000; // 10 days
    static final String TOKEN_PREFIX = "Bearer ";

    String generateToken(User user) {
        String[] roles = user.getRoles().stream().map(Role::toString).toArray(String[]::new);
        String token = JWT.create()
                .withSubject(user.getUsername())
                .withArrayClaim("role", roles)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        return TOKEN_PREFIX + token;
    }

    String getSubject(String token) {
        DecodedJWT decodedToken = validate(token);
        return decodedToken.getSubject();
    }

    private DecodedJWT validate(String token) {
        return JWT.require(HMAC512(SECRET.getBytes()))
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""));
    }
}
