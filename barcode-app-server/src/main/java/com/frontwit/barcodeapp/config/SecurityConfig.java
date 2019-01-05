package com.frontwit.barcodeapp.config;

import com.frontwit.barcodeapp.auth.JWTAuthenticationFilter;
import com.frontwit.barcodeapp.auth.JWTAuthorizationFilter;
import com.frontwit.barcodeapp.auth.Role;
import com.frontwit.barcodeapp.auth.UserDetailServiceImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String SECRET = "YmFyY29kZUFwcA==";
    public static final long EXPIRATION_TIME = 86_400_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTH_HEADER_STRING = "Authorization";

    private static final String REGISTER_URL = "/user/register";

    private UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //FIXME enable csrf
        http
                .csrf().disable()
                .authorizeRequests()
                // TODO usunac /** oraz synchronize
                .antMatchers(REGISTER_URL, "/api/orders/synchronize", "/**").permitAll()
                .antMatchers("/admin").hasRole(Role.ADMIN.toString())
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }


}
