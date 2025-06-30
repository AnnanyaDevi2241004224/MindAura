package com.MindAura.MindAura.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${alert.email.to}")
    private String alertEmailTo;

    public void sendCrisisAlert(String username, String content) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(alertEmailTo);
        msg.setSubject("🚨 Crisis Keyword Detected");
        msg.setText("User: " + username + "\n\nEntry: " + content);

        mailSender.send(msg);
    }
}

