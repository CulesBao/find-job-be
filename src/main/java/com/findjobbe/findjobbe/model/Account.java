package com.findjobbe.findjobbe.model;

import com.findjobbe.findjobbe.enums.Provider;
import com.findjobbe.findjobbe.enums.Role;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.*;

@Entity
@Table(name = "account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account extends AbstractEntity {
    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(nullable = false, length = 50)
    private String code;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_profile_id")
    private CandidateProfile candidateProfile;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "employer_profile_id")
    private EmployerProfile employerProfile;

  public boolean isActive() {
    return isActive;
  }
}