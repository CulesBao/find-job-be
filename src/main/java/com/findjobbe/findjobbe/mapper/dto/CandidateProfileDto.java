package com.findjobbe.findjobbe.mapper.dto;

import com.findjobbe.findjobbe.enums.Education;
import com.findjobbe.findjobbe.model.CandidateProfile;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
  private Optional<String> phoneNumber;
  private Optional<Date> dateOfBirth;
  private Optional<Boolean> gender;
  private Optional<Education> education;
  private Optional<String> avatarUrl;
  private Optional<String> bio;
  private Optional<ProvinceDto> province;
  private Optional<DistrictDto> district;
  private List<SocialLinkDto> socialLinks;
  public CandidateProfileDto(CandidateProfile candidateProfile) {
    this.id = candidateProfile.getId();
    this.firstName = candidateProfile.getFirstName();
    this.lastName = candidateProfile.getLastName();
    this.phoneNumber = Optional.ofNullable(candidateProfile.getPhoneNumber());
    this.dateOfBirth = Optional.ofNullable(candidateProfile.getDateOfBirth());
    this.bio = Optional.ofNullable(candidateProfile.getBio());
    this.avatarUrl = Optional.ofNullable(candidateProfile.getAvatarUrl());
    this.gender = Optional.ofNullable(candidateProfile.getGender());
    this.education = Optional.ofNullable(candidateProfile.getEducation());
    this.province = Optional.ofNullable(candidateProfile.getProvince()).map(ProvinceDto::new);
    this.district = Optional.ofNullable(candidateProfile.getDistrict()).map(DistrictDto::new);
    this.socialLinks =
        candidateProfile.getSocialLinks().stream()
            .map(SocialLinkDto::new)
            .collect(Collectors.toList());
  }
}
