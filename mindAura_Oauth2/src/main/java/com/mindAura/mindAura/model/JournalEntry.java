package com.mindAura.mindAura.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "journal_entries")
public class JournalEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private boolean isPublic;
    private String sentiment;
    private boolean isFlagged;

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public JournalEntry() {
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public boolean getIsPublic() { return isPublic; }

    public void setIsPublic(boolean isPublic) { this.isPublic = isPublic; }

    public String getSentiment() { return sentiment; }

    public void setSentiment(String sentiment) { this.sentiment = sentiment; }

    public boolean getIsFlagged() { return isFlagged; }

    public void setIsFlagged(boolean isFlagged) { this.isFlagged = isFlagged; }

    public LocalDateTime getTimestamp() { return timestamp; }

    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }
}
