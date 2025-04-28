package com.findjobbe.findjobbe.mapper.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.findjobbe.findjobbe.model.EmployerProfile;
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
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EmployerProfileDto extends BaseProfile {
  private UUID id;
  private String name;
  private String about;
  private String vision;
  private String websiteUrl;
  private String logoUrl;
  private String establishedIn;
  private ProvinceDto province;
  private DistrictDto district;
  private List<SocialLinkDto> socialLinks;
  private String createdAt;
  private String updatedAt;
  private String email;

  public EmployerProfileDto(EmployerProfile employerProfile) {
    this.id = employerProfile.getId();
    this.name = employerProfile.getName();
    this.about = employerProfile.getAbout();
    this.vision = employerProfile.getVision();
    this.websiteUrl = employerProfile.getWebsiteUrl();
    this.logoUrl = employerProfile.getLogoUrl();
    this.establishedIn = Optional.ofNullable(employerProfile.getEstablishedIn())
            .map(Object::toString)
            .orElse(null);
    this.province = new ProvinceDto(employerProfile.getProvince());
    this.district = new DistrictDto(employerProfile.getDistrict());
    this.socialLinks =
        Optional.ofNullable(employerProfile.getSocialLinks())
            .orElse(Collections.emptyList())
            .stream()
            .map(SocialLinkDto::new)
            .collect(Collectors.toList());
    this.createdAt = Optional.ofNullable(employerProfile.getCreatedAt())
            .map(Object::toString)
            .orElse(null);
    this.updatedAt = Optional.ofNullable(employerProfile.getUpdatedAt())
            .map(Object::toString)
            .orElse(null);
    this.email = employerProfile.getAccount().getEmail();
  }
}
