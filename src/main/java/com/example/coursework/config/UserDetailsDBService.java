package com.example.coursework.config;

import org.springframework.security.core.userdetails.UserDetails;
import com.example.coursework.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsDBService")
public class UserDetailsDBService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsDBService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElse(null);
    }
}