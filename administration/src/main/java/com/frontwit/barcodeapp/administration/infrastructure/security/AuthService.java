package com.frontwit.barcodeapp.administration.infrastructure.security;

import com.frontwit.barcodeapp.administration.infrastructure.security.Role.Type;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;


@Component
public class AuthService {

    private JwtUtils jwtUtil;

    private UserService userService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(JwtUtils jwtUtil, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    String authenticateUsingCredentials(final User user) {
        User userFromDb = userService.loadUserByUsername(user.getUsername());
        if (!bCryptPasswordEncoder.matches(user.getPassword(), userFromDb.getPassword())) {
            throw new BadCredentialsException("Provided credentials are incorrect");
        }
        setLoggedUser(userFromDb);
        return jwtUtil.generateToken(userFromDb);
    }

    void authenticateUsingToken(final String token) {
        String username = jwtUtil.getSubject(token);
        User user = userService.loadUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found : " + username);
        }
        setLoggedUser(user);
    }

    @RolesAllowed(Type.ADMIN)
    void register(final User user) {
        User userFromDb = userService.loadUserByUsername(user.getUsername());
        if (userFromDb != null) {
            throw new IllegalArgumentException("User " + user.getUsername() + " already exists");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(Arrays.asList(Role.USER)));
        userService.save(user);
    }

    void logout(final HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, null, auth);
        }
    }

    private void setLoggedUser(User user) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}