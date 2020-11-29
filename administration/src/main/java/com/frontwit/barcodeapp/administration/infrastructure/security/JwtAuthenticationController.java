package com.frontwit.barcodeapp.administration.infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
//@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public void register(@RequestBody User user) {
        authService.register(user);
    }
}
