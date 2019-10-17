package com.frontwit.barcodeapp.administration.infrastructure.security;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;


@Component
@AllArgsConstructor
public class AuthService {

    private JwtTokenUtil jwtTokenUtil;
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    String createToken(final UserDetails user) {
        var details = getUserDetails(user.getUsername());
        if (!bCryptPasswordEncoder.matches(user.getPassword(), details.getPassword())) {
            throw new BadCredentialsException("Provided credentials are incorrect");
        }
        return jwtTokenUtil.createToken(details);
    }

    void authorize(final String token) {
        jwtTokenUtil.validate(token);
        var username = jwtTokenUtil.getSubject(token);
        var user = userRepository.findByUsername(username);
        var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @RolesAllowed("ADMIN")
    void register(final UserDetails request) {
        var result = userRepository.findByUsername(request.getUsername());
        if (result != null) {
            throw new IllegalArgumentException("User " + request.getUsername() + " already exists");
        }

        var password = bCryptPasswordEncoder.encode(request.getPassword());
        var roles = new HashSet<>(Collections.singletonList(Role.USER));
        var newUser = new User(request.getUsername(), password, roles);
        userRepository.save(newUser);
    }

    void logout(final HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, null, auth);
        }
    }

    private UserDetails getUserDetails(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username))
                .orElseThrow(() -> new AuthenticationServiceException("User does not exist"));
    }
}