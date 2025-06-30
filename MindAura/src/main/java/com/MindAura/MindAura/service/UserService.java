//package com.MindAura.MindAura.service;
//
//import com.MindAura.MindAura.model.User;
//import com.MindAura.MindAura.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class UserService {
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public User registerUser(String username, String rawPassword) {
//        String hashed = passwordEncoder.encode(rawPassword); // HASH here
//        User user = User.builder()
//                .username(username)
//                .password(hashed)
//                .role("USER")
//                .build();
//        return userRepository.save(user);
//    }
//
//    public User findByUsername(String username) {
//        return userRepository.findByUsername(username).orElseThrow();
//    }
//}

package com.MindAura.MindAura.service;

import com.MindAura.MindAura.model.User;
import com.MindAura.MindAura.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(String username, String rawPassword) {
        String hashed = passwordEncoder.encode(rawPassword); // ✅ This hashes the password
        User user = User.builder()
                .username(username)
                .password(hashed)
                .role("USER")
                .build();
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }
}

