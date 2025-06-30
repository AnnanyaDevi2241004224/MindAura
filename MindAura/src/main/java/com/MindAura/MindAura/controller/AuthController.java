package com.MindAura.MindAura.controller;

import com.MindAura.MindAura.model.User;
import com.MindAura.MindAura.service.UserService;
import com.MindAura.MindAura.config.JwtUtil;
import com.MindAura.MindAura.payLoad.AuthRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        User newUser = userService.registerUser(user.getUsername(), user.getPassword());
        String token = jwtUtil.generateToken(newUser.getUsername(), newUser.getRole());
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            User existing = userService.findByUsername(request.getUsername());
            String token = jwtUtil.generateToken(existing.getUsername(), existing.getRole());
            return ResponseEntity.ok(Map.of("token", token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/api/test/password-match")
    public boolean testPasswordMatch() {
        String rawPassword = "anna1988";
        String hashed = "$2a$10$vl6IhIm6MTHPPMk4mQK9NOliAfmrSMwJPL9mEK1bZ63IHHcbFZzce";
        return passwordEncoder.matches(rawPassword, hashed);
    }

}
