package com.example.coursework.controllers;

import com.example.coursework.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.coursework.models.*;

@Controller
public class AuthenticationController {

    private final UserAuthenticationResponse userAuthenticationResponse;
    private final UserRegisterResponse userRegisterResponse;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public AuthenticationController(UserAuthenticationResponse userAuthenticationResponse,
                                    UserRegisterResponse userRegisterResponse,
                                    UserRepository userRepository,
                                    PasswordEncoder encoder) {
        this.userAuthenticationResponse = userAuthenticationResponse;
        this.userRegisterResponse = userRegisterResponse;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("userAuthentication", userAuthenticationResponse);
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userRegisterResponse", userRegisterResponse);
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute @Valid UserRegisterResponse userRegisterResponse,
                                  Errors errors, Model model) {

        if (!userRegisterResponse.getPassword().equals(userRegisterResponse.getPasswordConfirmation())) {
            model.addAttribute("passwordsNotEqual", "Passwords are not equal");
        }
        if (userRepository.findByUsername(userRegisterResponse.getUsername()).orElse(null) != null) {
            model.addAttribute("usernameIsTaken", "Username is already taken");
        }
        if (errors.hasErrors()
                || model.containsAttribute("passwordsNotEqual")
                || model.containsAttribute("usernameIsTaken")
        ) {
            return "register";
        }

        var user = User.builder()
                .name(userRegisterResponse.getName())
                .username(userRegisterResponse.getUsername())
                .password(encoder.encode(userRegisterResponse.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);

        return "redirect:/login";
    }
}
