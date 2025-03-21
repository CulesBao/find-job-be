package com.findjobbe.findjobbe.model;

import com.findjobbe.findjobbe.enums.SocialLinkType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "social_links")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocialLink extends AbstractEntity {
  @Column(nullable = false, name = "type")
  @Enumerated(EnumType.STRING)
  private SocialLinkType type;

  @Column(nullable = false)
  private String url;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "candidate_profile_id", referencedColumnName = "id")
  private CandidateProfile candidateProfile;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "employer_profile_id", referencedColumnName = "id")
  private EmployerProfile employerProfile;
}
