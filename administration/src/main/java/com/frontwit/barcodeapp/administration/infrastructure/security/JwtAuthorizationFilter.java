package com.frontwit.barcodeapp.administration.infrastructure.security;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!request.getRequestURI().contains("/login")) {
            var token = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (token != null && !token.isEmpty()) {
                authService.authorize(token);
            }
        }
        filterChain.doFilter(request, response);
    }
}
