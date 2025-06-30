package com.mindAura.mindAura.repository;


import com.mindAura.mindAura.model.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {
    List<JournalEntry> findByUserId(Long userId);
    List<JournalEntry> findByIsFlaggedTrue();
}
