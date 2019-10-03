package com.frontwit.barcodeapp.administration.infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public void register(@RequestBody User user) {
        authService.register(user);
    }

    @CrossOrigin
    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity login(@RequestBody User user) {
        String token = authService.createToken(user);
        return ResponseEntity.ok()
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.AUTHORIZATION)
                .header(HttpHeaders.AUTHORIZATION, token)
                .build();
    }
//
//    @CrossOrigin
//    @PostMapping(value = "/createToken")
//    public ResponseEntity createToken(@RequestBody String token) {
//        securityService.verifyToken(token);
//        return ResponseEntity.ok()
//                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.AUTHORIZATION)
//                .header(HttpHeaders.AUTHORIZATION, token)
//                .build();
//    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, null, auth);
        }
    }
}
