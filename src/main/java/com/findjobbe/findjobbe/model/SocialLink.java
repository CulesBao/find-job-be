package com.findjobbe.findjobbe.model;

import com.findjobbe.findjobbe.enums.SocialLinkType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "social_links")
@Getter
@Setter
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
