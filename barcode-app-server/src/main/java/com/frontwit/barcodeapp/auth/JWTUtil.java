package com.frontwit.barcodeapp.auth;


import com.auth0.jwt.JWT;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Service
public class JWTUtil {

    private static final String SECRET = "YmFyY29kZUFwcA==";
    private static final long EXPIRATION_TIME = 86_400_000; // 10 days


    // TODO wygenerowac token, ustalic waznosc
    public String generateToken(User user) {
        String[] roles = user.getRoles().stream().map(Role::toString).toArray(String[]::new);
        return JWT.create()
                .withSubject(user.getUsername())
                .withArrayClaim("role", roles)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
    }

    // TODO spr czy token wazny
    public boolean isValid(String token) {
        return true;
    }

    // TODO odkodowac token i zwrocic usera
    public String getUsername(String token) {
        return "DUPA";
    }
}
