package com.findjobbe.findjobbe.mapper.dto;

import com.findjobbe.findjobbe.enums.Education;
import com.findjobbe.findjobbe.model.CandidateProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Builder
public class CandidateProfileDto {
    @Builder
    public CandidateProfileDto(CandidateProfile candidateProfile) {}
    private UUID id;
    private String firstName;
    private String lastName;
    private Optional<String> phoneNumber;
    private Optional<LocalDateTime> dateOfBirth;
    private Optional<Boolean> gender;
    private Optional<Education> education;
    private Optional<String> avatarUrl;
    private Optional<String> bio;
    private Optional<ProvinceDto> province;
    private Optional<DistrictDto> district;
    private List<SocialLinkDto> socialLinks;
}
