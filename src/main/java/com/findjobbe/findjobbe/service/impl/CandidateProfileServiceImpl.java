package com.findjobbe.findjobbe.service.impl;

import com.findjobbe.findjobbe.exception.BadRequestException;
import com.findjobbe.findjobbe.exception.MessageConstants;
import com.findjobbe.findjobbe.exception.NotFoundException;
import com.findjobbe.findjobbe.mapper.dto.BaseProfile;
import com.findjobbe.findjobbe.mapper.dto.CandidateProfileDto;
import com.findjobbe.findjobbe.mapper.dto.SocialLinkDto;
import com.findjobbe.findjobbe.mapper.request.CandidateProfileRequest;
import com.findjobbe.findjobbe.model.*;
import com.findjobbe.findjobbe.repository.*;
import com.findjobbe.findjobbe.service.IAccountService;
import com.findjobbe.findjobbe.service.IFileService;
import com.findjobbe.findjobbe.service.IProfileService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CandidateProfileServiceImpl implements IProfileService {
  private final IAccountService accountService;
  private final AccountRepository accountRepository;
  private final CandidateProfileRepository candidateProfileRepository;
  private final DistrictServiceImpl districtServiceImpl;
  private final SocialLinkRepository socialLinkRepository;
  private final ProvinceServiceImpl provinceServiceImpl;
  private final IFileService cloudinaryService;

  @Value("${default-candidate-avatar}")
  private String defaultCandidateAvatar;

  @Autowired
  public CandidateProfileServiceImpl(
      IAccountService accountService,
      CandidateProfileRepository candidateProfileRepository,
      DistrictServiceImpl districtServiceImpl,
      SocialLinkRepository socialLinkRepository,
      ProvinceServiceImpl provinceServiceImpl,
      IFileService cloudinaryService,
      AccountRepository accountRepository) {
    this.accountService = accountService;
    this.candidateProfileRepository = candidateProfileRepository;
    this.districtServiceImpl = districtServiceImpl;
    this.socialLinkRepository = socialLinkRepository;
    this.provinceServiceImpl = provinceServiceImpl;
    this.cloudinaryService = cloudinaryService;
    this.accountRepository = accountRepository;
  }

  @Override
  @Transactional
  public BaseProfile createProfile(String accountId, BaseProfile profileRequest) {
    CandidateProfileRequest candidateProfileRequest = (CandidateProfileRequest) profileRequest;
    Account account = accountService.getAccountById(accountId);
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
            .avatarUrl(defaultCandidateAvatar)
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

  @Override
  public void updateProfileImage(String accountId, MultipartFile image) {
    try {
      String imgUrl = cloudinaryService.uploadImage(image);
      CandidateProfile candidateProfile =
          candidateProfileRepository
              .findByAccountId(UUID.fromString(accountId))
              .orElseThrow(() -> new NotFoundException(MessageConstants.PROFILE_NOT_FOUND));
      candidateProfile.setAvatarUrl(imgUrl);
      candidateProfileRepository.save(candidateProfile);
    } catch (Exception e) {
      throw new BadRequestException(MessageConstants.IMAGE_UPLOAD_FAILED);
    }
  }
}
