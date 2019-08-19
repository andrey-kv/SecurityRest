package com.learnspring.SecurityRest.controllers;

import com.learnspring.SecurityRest.configs.JwtTokenProvider;
import com.learnspring.SecurityRest.models.User;
import com.learnspring.SecurityRest.repositories.UserRepository;
import com.learnspring.SecurityRest.services.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository users;

    @Autowired
    private CustomUserDetailsService userService;

    // @SuppressWarnings("rawtypes")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthBody data) {
        String username = data.getEmail();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
        String token = jwtTokenProvider.createToken(username, this.users.findByEmail(username).getRoles());

        log.info("Endpoint: POST /login, user authenticated: " + username);

        Map<Object, Object> model = new HashMap<>();
        model.put("username", username);
        model.put("token", token);
        return ResponseEntity.ok(model);
    }

    // @SuppressWarnings("rawtypes")
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody User user) {
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            throw new BadCredentialsException("User with username: " + user.getEmail() + " already exists");
        }
        userService.saveUser(user);
        log.info("Endpoint: POST /register, user registered: " + user.toString());
        Map<Object, Object> model = new HashMap<>();
        model.put("message", "User registered successfully");
        return ResponseEntity.ok(model);
    }
}
