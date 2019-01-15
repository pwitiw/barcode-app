package com.frontwit.barcodeapp.auth;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.frontwit.barcodeapp.common.Msg.USER_NOT_FOUND;

@Service
public class UserDetailService implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
       return userRepository.findByUsername(username);
    }
}
