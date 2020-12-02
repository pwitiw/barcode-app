package com.frontwit.barcodeapp.administration.infrastructure.security.config;

import com.frontwit.barcodeapp.administration.infrastructure.security.AuthProvider;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Profile("prod")
@AllArgsConstructor
public class ProdSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthProvider authProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();

        http
                .authorizeRequests()
                .anyRequest().authenticated();
        http
                .formLogin()
                .permitAll();
        http
                .logout()
                .permitAll();
    }
}
