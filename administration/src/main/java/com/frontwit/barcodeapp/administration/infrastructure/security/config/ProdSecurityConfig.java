package com.frontwit.barcodeapp.administration.infrastructure.security.config;

import com.frontwit.barcodeapp.administration.infrastructure.security.AuthService;
import com.frontwit.barcodeapp.administration.infrastructure.security.JwtAuthorizationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@EnableWebSecurity
@AllArgsConstructor
@Profile("prod")
public class ProdSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String API_URL = "/api/**";

    private AuthService authService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
//        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers(API_URL).authenticated()
                .anyRequest().permitAll();

        http.addFilterBefore(new JwtAuthorizationFilter(authService), FilterSecurityInterceptor.class);
    }
}
