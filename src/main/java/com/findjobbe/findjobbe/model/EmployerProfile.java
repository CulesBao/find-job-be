package com.findjobbe.findjobbe.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employer_profile")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployerProfile extends AbstractEntity {
  @Column(nullable = false)
  private String name;

  @Column private String about;

  @Column private String vision;

  @Column private String website_url;

  @Column private String logo_url;

  @Column(name = "established_in")
  private Date establishedIn;

  @OneToOne(mappedBy = "employerProfile", cascade = CascadeType.ALL)
  @JoinColumn(name = "account_id")
  private Account account;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "province_id")
  private Province province;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "district_id")
  private District district;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "employerProfile")
  private List<SocialLink> socialLinks;

  @OneToMany(mappedBy = "employerProfile", cascade = CascadeType.ALL)
  private List<Job> jobs;
}
