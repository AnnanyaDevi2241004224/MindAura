package com.mindAura.mindAura.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("/user")
    public Object user(@AuthenticationPrincipal OAuth2User principal) {
        return principal.getAttributes(); // returns name, email, etc.
    }
}

