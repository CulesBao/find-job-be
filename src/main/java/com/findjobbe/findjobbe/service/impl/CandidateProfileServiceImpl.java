package com.findjobbe.findjobbe.service.impl;

import com.findjobbe.findjobbe.exception.MessageConstants;
import com.findjobbe.findjobbe.exception.NotFoundException;
import com.findjobbe.findjobbe.mapper.dto.BaseProfile;
import com.findjobbe.findjobbe.mapper.dto.CandidateProfileDto;
import com.findjobbe.findjobbe.mapper.dto.SocialLinkDto;
import com.findjobbe.findjobbe.mapper.request.CandidateProfileRequest;
import com.findjobbe.findjobbe.model.*;
import com.findjobbe.findjobbe.repository.*;
import com.findjobbe.findjobbe.service.IProfileService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidateProfileServiceImpl implements IProfileService {
  private final AccountRepository accountRepository;
  private final CandidateProfileRepository candidateProfileRepository;
  private final DistrictServiceImpl districtServiceImpl;
  private final SocialLinkRepository socialLinkRepository;
  private final ProvinceServiceImpl provinceServiceImpl;

  @Autowired
  public CandidateProfileServiceImpl(
      AccountRepository accountRepository,
      CandidateProfileRepository candidateProfileRepository,
      DistrictServiceImpl districtServiceImpl,
      SocialLinkRepository socialLinkRepository,
      ProvinceServiceImpl provinceServiceImpl) {
    this.accountRepository = accountRepository;
    this.candidateProfileRepository = candidateProfileRepository;
    this.districtServiceImpl = districtServiceImpl;
    this.socialLinkRepository = socialLinkRepository;
    this.provinceServiceImpl = provinceServiceImpl;
  }

  @Override
  @Transactional
  public BaseProfile createProfile(String accountId, BaseProfile profileRequest) {
    CandidateProfileRequest candidateProfileRequest = (CandidateProfileRequest) profileRequest;
    Account account =
        accountRepository
            .findById(UUID.fromString(accountId))
            .orElseThrow(() -> new NotFoundException(MessageConstants.ACCOUNT_NOT_FOUND));
    Province province =
        provinceServiceImpl.getProvinceByCode(candidateProfileRequest.getProvinceCode());
    District district =
        districtServiceImpl.getDistrictByCodeAndProvinceCode(
            candidateProfileRequest.getDistrictCode(), candidateProfileRequest.getProvinceCode());
    CandidateProfile candidateProfile =
        CandidateProfile.builder()
            .firstName(candidateProfileRequest.getFirstName())
            .lastName(candidateProfileRequest.getLastName())
            .phoneNumber(candidateProfileRequest.getPhoneNumber())
            .dateOfBirth(candidateProfileRequest.getDateOfBirth())
            .gender(candidateProfileRequest.getGender())
            .education(candidateProfileRequest.getEducation())
            .bio(candidateProfileRequest.getBio())
            .avatarUrl(null)
            .province(province)
            .district(district)
            .account(account)
            .socialLinks(new ArrayList<>())
            .build();

    candidateProfile = candidateProfileRepository.save(candidateProfile);

    account.setCandidateProfile(candidateProfile);
    accountRepository.save(account);

    return new CandidateProfileDto(candidateProfile);
  }

  @Override
  public BaseProfile getProfile(String profileId) {
    CandidateProfile candidateProfile =
        candidateProfileRepository
            .findById(UUID.fromString(profileId))
            .orElseThrow(() -> new NotFoundException(MessageConstants.PROFILE_NOT_FOUND));
    return new CandidateProfileDto(candidateProfile);
  }

  @Override
  @Transactional
  public void updateSocialLinks(String accountId, List<SocialLinkDto> socialLinkDtos) {
    CandidateProfile candidateProfile =
        candidateProfileRepository
            .findByAccountId(UUID.fromString(accountId))
            .orElseThrow(() -> new NotFoundException(MessageConstants.PROFILE_NOT_FOUND));
    socialLinkRepository.deleteByCandidateProfile(candidateProfile);
    List<SocialLink> socialLinkList =
        socialLinkDtos.stream()
            .map(
                socialLinkDto ->
                    new SocialLink(
                        socialLinkDto.getType(), socialLinkDto.getUrl(), candidateProfile, null))
            .toList();
    socialLinkRepository.saveAll(socialLinkList);
  }

  @Override
  @Transactional
  public void updateProfile(String accountId, BaseProfile profileRequest) {
    CandidateProfile candidateProfile =
        candidateProfileRepository
            .findByAccountId(UUID.fromString(accountId))
            .orElseThrow(() -> new NotFoundException(MessageConstants.PROFILE_NOT_FOUND));
    CandidateProfileRequest candidateProfileRequest = (CandidateProfileRequest) profileRequest;
    Province province =
        provinceServiceImpl.getProvinceByCode(candidateProfileRequest.getProvinceCode());
    District district =
        districtServiceImpl.getDistrictByCodeAndProvinceCode(
            candidateProfileRequest.getDistrictCode(), candidateProfileRequest.getProvinceCode());
    candidateProfile.update(candidateProfileRequest);
    candidateProfile.setProvince(province);
    candidateProfile.setDistrict(district);
    candidateProfileRepository.save(candidateProfile);
  }
}
