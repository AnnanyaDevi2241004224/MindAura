package com.MindAura.MindAura.controller;

import com.MindAura.MindAura.model.JournalEntry;
import com.MindAura.MindAura.repository.JournalRepository;
import com.MindAura.MindAura.config.JwtUtil;
import com.MindAura.MindAura.service.EmailService;
import com.MindAura.MindAura.service.SentimentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@CrossOrigin(origins = "http://127.0.0.1:5500")

@RestController
@RequestMapping("/api/journals")
@RequiredArgsConstructor
public class JournalController {


    private final JournalRepository journalRepository;
    private final JwtUtil jwtUtil;
    private final SentimentService sentimentService;
    private final EmailService emailService;

    private static final List<String> CRISIS_KEYWORDS = List.of("suicide", "depressed", "worthless");

    @PostMapping("/submit")
    public ResponseEntity<?> submitEntry(@RequestHeader("Authorization") String authHeader,
                                         @RequestBody Map<String, Object> payload) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);

        String content = (String) payload.get("content");
        boolean isPublic = (Boolean) payload.get("isPublic");

        boolean isFlagged = CRISIS_KEYWORDS.stream().anyMatch(content.toLowerCase()::contains);
        String sentiment = sentimentService.analyzeSentiment(content);

        JournalEntry entry = JournalEntry.builder()
                .username(username)
                .content(content)
                .isPublic(isPublic)
                .isFlagged(isFlagged)
                .createdAt(LocalDateTime.now())
                .sentiment(sentiment)
                .build();

        journalRepository.save(entry);

        if (isFlagged) {
            emailService.sendCrisisAlert(username, content);
        }

        return ResponseEntity.ok("Entry saved with sentiment: " + sentiment);
    }


    @GetMapping("/my")
    public ResponseEntity<?> getMyEntries(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        return ResponseEntity.ok(journalRepository.findByUsername(username));
    }

    @GetMapping("/public")
    public ResponseEntity<?> getPublicEntries() {
        return ResponseEntity.ok(journalRepository.findByIsPublicTrue());
    }

    @GetMapping("/admin/flagged")
    public ResponseEntity<?> getFlaggedEntries() {
        return ResponseEntity.ok(journalRepository.findByIsFlaggedTrue());
    }
}

