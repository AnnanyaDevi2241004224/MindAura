package com.mindAura.mindAura.service;

import com.mindAura.mindAura.model.User;
import com.mindAura.mindAura.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) {
        OAuth2User oAuth2User = super.loadUser(request);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        System.out.println("➡️ OAuth2 login received for email: " + email);

        Optional<User> existingUserOpt = userRepository.findByEmail(email);

        String role = "USER";
        if (email.equalsIgnoreCase("your-email@gmail.com")) {
            role = "ADMIN"; // set your admin email
        }

        if (existingUserOpt.isEmpty()) {
            User newUser = new User(name, email, role, "google");
            userRepository.save(newUser);
            System.out.println("✅ New user saved to DB: " + email);
        } else {
            User existingUser = existingUserOpt.get();
            if (!existingUser.getRole().equals(role)) {
                existingUser.setRole(role);
                userRepository.save(existingUser);
                System.out.println("♻️ Updated user role for: " + email);
            } else {
                System.out.println("ℹ️ User already exists: " + email);
            }
        }

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(role)),
                attributes,
                "email"
        );
    }
}
