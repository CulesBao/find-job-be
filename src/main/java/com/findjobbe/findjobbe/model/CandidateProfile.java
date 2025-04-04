package com.findjobbe.findjobbe.model;

import com.findjobbe.findjobbe.enums.Education;
import com.findjobbe.findjobbe.mapper.request.CandidateProfileRequest;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import lombok.*;

@Entity
@Table(name = "candidate_profile")
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CandidateProfile extends AbstractEntity {
  @Column(nullable = false, name = "first_name")
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "date_of_birth")
  private Date dateOfBirth;

  @Column private Boolean gender;

  @Column
  @Enumerated(EnumType.STRING)
  private Education education;

  @Column(name = "avatar_url")
  private String avatarUrl;

  @Column private String bio;

  @OneToOne(cascade = CascadeType.ALL)
  private Account account;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "province_id")
  private Province province;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "district_id")
  private District district;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidateProfile")
  private List<SocialLink> socialLinks;

  @OneToMany(mappedBy = "candidateProfile", cascade = CascadeType.ALL)
  private List<Application> applications;

  public void update(CandidateProfileRequest candidateProfileRequest) {
    this.firstName = candidateProfileRequest.getFirstName();
    this.lastName = candidateProfileRequest.getLastName();
    this.phoneNumber = candidateProfileRequest.getPhoneNumber();
    this.dateOfBirth = candidateProfileRequest.getDateOfBirth();
    this.gender = candidateProfileRequest.getGender();
    this.education = candidateProfileRequest.getEducation();
    this.bio = candidateProfileRequest.getBio();
  }
}
