// --- ✅ UPDATED JournalController.java ---

package com.mindAura.mindAura.controller;

import com.mindAura.mindAura.model.*;
import com.mindAura.mindAura.repository.*;
import com.mindAura.mindAura.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/journal")
@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
public class JournalController {

    @Autowired
    private JournalEntryRepository journalRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private sentimentService sentimentService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/submit")
    public String submitJournal(@AuthenticationPrincipal OAuth2User principal,
                                @RequestBody JournalEntry entry) {
        try {
            if (principal == null) {
                return "Failed to submit journal: User not authenticated.";
            }
            String email = principal.getAttribute("email");
            if (email == null) {
                return "Failed to submit journal: No email present in principal.";
            }

            User user = userRepo.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));

            String content = entry.getContent();
            System.out.println("Received entry: " + content);

            String sentiment = sentimentService.analyzeSentiment(content);
            boolean isFlagged = content.toLowerCase().contains("suicide") ||
                    sentiment.equalsIgnoreCase("NEGATIVE");

            entry.setUser(user);
            entry.setSentiment(sentiment);
            entry.setIsFlagged(isFlagged);
            entry.setTimestamp(LocalDateTime.now());

            journalRepo.save(entry);

            if (isFlagged) {
                emailService.sendAlert(content);
            }

            return "Entry submitted with sentiment: " + sentiment;
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to submit journal: " + e.getMessage();
        }
    }

    @PostMapping("/anonymous")
    public String submitAnonymous(@RequestBody JournalEntry entry) {
        String content = entry.getContent();
        String sentiment = sentimentService.analyzeSentiment(content);
        boolean isFlagged = content.toLowerCase().contains("suicide") || sentiment.equalsIgnoreCase("NEGATIVE");

        entry.setUser(null);
        entry.setSentiment(sentiment);
        entry.setIsFlagged(isFlagged);
        entry.setTimestamp(LocalDateTime.now());

        journalRepo.save(entry);

        if (isFlagged) {
            emailService.sendAlert(content);
        }

        return "Anonymous entry submitted with sentiment: " + sentiment;
    }

    @GetMapping("/my")
    public List<JournalEntry> getMyEntries(@AuthenticationPrincipal OAuth2User principal) {
        String email = principal.getAttribute("email");
        User user = userRepo.findByEmail(email).orElseThrow();
        return journalRepo.findByUserId(user.getId());
    }

    @GetMapping("/flagged")
    public List<JournalEntry> getFlaggedEntries() {
        return journalRepo.findByIsFlaggedTrue();
    }
}