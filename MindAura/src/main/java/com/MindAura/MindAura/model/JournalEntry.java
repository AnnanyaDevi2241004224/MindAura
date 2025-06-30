package com.MindAura.MindAura.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JournalEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(length = 2000)
    private String content;

    private boolean isPublic;

    private boolean isFlagged;

    private LocalDateTime createdAt;

    private String sentiment;
}

