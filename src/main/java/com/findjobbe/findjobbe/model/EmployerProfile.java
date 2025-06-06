package com.findjobbe.findjobbe.model;

import com.findjobbe.findjobbe.mapper.request.EmployerProfileRequest;
import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.*;

@Entity
@Table(name = "employer_profile")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployerProfile extends AbstractEntity {
  @Column(nullable = false)
  private String name;

  @Column(length = 2500)
  private String about;

  @Column(length = 2500)
  private String vision;

  @Column private String website_url;

  @Column private String logo_url;

  @Column(name = "established_in")
  private Date establishedIn;

  @OneToOne(mappedBy = "employerProfile", cascade = CascadeType.ALL)
  @JoinColumn(name = "account_id")
  private Account account;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "province_id")
  private Province province;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "district_id")
  private District district;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "employerProfile")
  private List<SocialLink> socialLinks;

  @OneToMany(mappedBy = "employerProfile", cascade = CascadeType.ALL)
  private List<Job> jobs;

  @ManyToMany
  @JoinTable(
      name = "saved_candidates",
      joinColumns = @JoinColumn(name = "employer_id"),
      inverseJoinColumns = @JoinColumn(name = "candidate_id"))
  private Set<CandidateProfile> savedCandidates = new HashSet<>();

  public String getLogoUrl() {
    return logo_url;
  }

  public String getWebsiteUrl() {
    return website_url;
  }

  public void update(EmployerProfileRequest employerProfileRequest) {
    this.name = employerProfileRequest.getName();
    this.about = employerProfileRequest.getAbout();
    this.vision = employerProfileRequest.getVision();
    this.website_url = employerProfileRequest.getWebsiteUrl();
    this.establishedIn = employerProfileRequest.getEstablishedIn();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    EmployerProfile that = (EmployerProfile) o;
    return this.getId() != null && this.getId().equals(that.getId());
  }

  @Override
  public int hashCode() {
    return 31;
  }
}
