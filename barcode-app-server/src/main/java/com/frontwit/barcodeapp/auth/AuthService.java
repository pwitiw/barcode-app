package com.frontwit.barcodeapp.auth;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


// TODO pytanie czy w JWT nie jest czasami haslo hashowane, wypadaloby sprawdzic haslo czy przypadkiem nie zmienione?!
@Component
public class AuthService {

    static final String TOKEN_PREFIX = "Bearer ";

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    String getTokenForCredentials(User user) {
        User userFromDb = userDetailService.loadUserByUsername(user.getUsername());
        if (bCryptPasswordEncoder.matches(user.getPassword(),userFromDb.getPassword())) {
            return jwtUtil.generateToken(userFromDb);
        }
        return null;
    }

    // TODO - odkodowac token, sprawdzic usera czy istnieje nie jest usuniety, sprawdzic hash
    //    UsernamePasswordAuthenticationToken
    void authenticateUsingToken(String token) {
        if (jwtUtil.isValid(token)) {
            String username = jwtUtil.getUsername(token);
            User user = userDetailService.loadUserByUsername(username);
            setLoggedUser(user);
        }
    }

    // TODO - spr czy admin (ROlesAllowed), zahashowac haslo, przypisac role
    void register(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(Sets.immutableEnumSet(Role.READ_ONLY));
        userDetailService.save(user);
    }

    void logout(HttpServletRequest request) {
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
