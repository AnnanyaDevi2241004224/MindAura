package com.mindAura.mindAura.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<JournalEntry> journalEntries = new ArrayList<>();


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String role;  // e.g., "USER" or "ADMIN"
    private String provider; // e.g., "google"

    public User() {}

    public User(String name, String email, String role, String provider) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.provider = provider;
    }

    // Getters and setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }

    public String getProvider() { return provider; }

    public void setProvider(String provider) { this.provider = provider; }
}
