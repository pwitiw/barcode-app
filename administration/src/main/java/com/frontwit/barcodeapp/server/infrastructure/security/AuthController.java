package com.frontwit.barcodeapp.server.infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public void register(@RequestBody User user) {
        authService.register(user);
    }

    @CrossOrigin
    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity login(@RequestBody User user) {
        String token = authService.authenticateUsingCredentials(user);
        return ResponseEntity.ok()
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.AUTHORIZATION)
                .header(HttpHeaders.AUTHORIZATION, token)
                .build();
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        authService.logout(request);
    }
}
