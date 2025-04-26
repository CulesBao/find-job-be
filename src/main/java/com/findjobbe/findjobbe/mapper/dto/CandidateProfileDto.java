package com.findjobbe.findjobbe.mapper.dto;

import com.findjobbe.findjobbe.enums.Education;
import com.findjobbe.findjobbe.model.CandidateProfile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Setter
@Getter
public class CandidateProfileDto extends BaseProfile {
  private UUID id;
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private String dateOfBirth;
  private Boolean gender;
  private Education education;
  private String avatarUrl;
  private String bio;
  private ProvinceDto province;
  private DistrictDto district;
  private List<SocialLinkDto> socialLinks;
  private String createdAt;
  private String updatedAt;
  private String email;

  public CandidateProfileDto(CandidateProfile candidateProfile) {
    this.id = candidateProfile.getId();
    this.firstName = candidateProfile.getFirstName();
    this.lastName = candidateProfile.getLastName();
    this.phoneNumber = candidateProfile.getPhoneNumber();
    this.dateOfBirth = candidateProfile.getDateOfBirth().toString();
    this.bio = candidateProfile.getBio();
    this.avatarUrl = candidateProfile.getAvatarUrl();
    this.gender = candidateProfile.getGender();
    this.education = candidateProfile.getEducation();
    this.province = new ProvinceDto(candidateProfile.getProvince());
    this.district = new DistrictDto(candidateProfile.getDistrict());
    this.socialLinks =
        Optional.ofNullable(candidateProfile.getSocialLinks())
            .orElse(Collections.emptyList())
            .stream()
            .map(SocialLinkDto::new)
            .collect(Collectors.toList());
    this.createdAt = candidateProfile.getCreatedAt().toString();
    this.updatedAt = candidateProfile.getUpdatedAt().toString();
    this.email = candidateProfile.getAccount().getEmail();
  }
}
