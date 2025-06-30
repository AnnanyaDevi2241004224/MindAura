package com.mindAura.mindAura.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendAlert(String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("admin@example.com");
        message.setSubject("🚨 MindAura: Flagged Entry Alert");
        message.setText("A flagged journal entry was detected:\n\n" + content);
        mailSender.send(message);
    }
}
