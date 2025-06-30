package com.MindAura.MindAura.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "User") // Avoid using reserved keyword
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String role; // "USER" or "ADMIN"
}
