package com.frontwit.barcodeapp.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.frontwit.barcodeapp.common.Msg.USER_NOT_FOUND;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserDetails userDetails = userRepository.findByUsername(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException(USER_NOT_FOUND + ": " + username);
        }
        return userDetails;
    }
}
