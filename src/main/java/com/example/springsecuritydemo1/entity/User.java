package com.example.springsecuritydemo1.entity;

import com.example.springsecuritydemo1.enums.Roles;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String email;
    private String password;
    private String confirmPassword;
    private Roles roles;
    private Boolean IsVerified = false;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastLogin;
}
