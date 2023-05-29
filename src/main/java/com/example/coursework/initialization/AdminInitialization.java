package com.example.coursework.initialization;

import com.example.coursework.models.Role;
import com.example.coursework.models.User;
import com.example.coursework.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class AdminInitialization implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminInitialization(UserRepository userRepository,
                            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findFirstByRole(Role.ADMIN).isEmpty()) {
            var admin = User.builder()
                    .role(Role.ADMIN)
                    .username("admin")
                    .name("admin")
                    .password(passwordEncoder.encode("admin"))
                    .build();
            userRepository.save(admin);
        }
    }
}