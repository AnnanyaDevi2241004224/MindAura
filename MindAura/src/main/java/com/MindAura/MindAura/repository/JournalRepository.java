package com.MindAura.MindAura.repository;


import com.MindAura.MindAura.model.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JournalRepository extends JpaRepository<JournalEntry, Long> {
    List<JournalEntry> findByUsername(String username);
    List<JournalEntry> findByIsPublicTrue();
    List<JournalEntry> findByIsFlaggedTrue();
}

