package com.frontwit.barcodeapp.auth;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
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

import static com.frontwit.barcodeapp.common.Msg.USER_NOT_FOUND;


@Component
public class AuthService {

    @Autowired
    private JwtUtils jwtUtil;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    String authenticateUsingCredentials(final User user) {
        User userFromDb = userDetailService.loadUserByUsername(user.getUsername());
        if (!bCryptPasswordEncoder.matches(user.getPassword(), userFromDb.getPassword())) {
            throw new BadCredentialsException("Provided credentials are incorrect");
        }
        setLoggedUser(userFromDb);
        return jwtUtil.generateToken(userFromDb);
    }

    void authenticateUsingToken(final String token) {
        String username = jwtUtil.getSubject(token);
        User user = userDetailService.loadUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(USER_NOT_FOUND + ": " + username);
        }
        setLoggedUser(user);
    }

    @RolesAllowed(Role.Type.ADMIN)
    void register(final User user) {
        User userFromDb = userDetailService.loadUserByUsername(user.getUsername());
        if (userFromDb != null) {
            throw new IllegalArgumentException("User " + user.getUsername() + " already exists");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(Sets.immutableEnumSet(Role.USER));
        userDetailService.save(user);
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