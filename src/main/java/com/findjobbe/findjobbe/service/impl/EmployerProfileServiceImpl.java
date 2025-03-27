package com.findjobbe.findjobbe.service.impl;

import com.findjobbe.findjobbe.exception.BadRequestException;
import com.findjobbe.findjobbe.exception.ForbiddenException;
import com.findjobbe.findjobbe.exception.MessageConstants;
import com.findjobbe.findjobbe.exception.NotFoundException;
import com.findjobbe.findjobbe.mapper.dto.BaseProfile;
import com.findjobbe.findjobbe.mapper.dto.EmployerProfileDto;
import com.findjobbe.findjobbe.mapper.dto.SocialLinkDto;
import com.findjobbe.findjobbe.mapper.request.EmployerProfileRequest;
import com.findjobbe.findjobbe.model.*;
import com.findjobbe.findjobbe.repository.AccountRepository;
import com.findjobbe.findjobbe.repository.EmployerProfileRepository;
import com.findjobbe.findjobbe.repository.SocialLinkRepository;
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
public class EmployerProfileServiceImpl implements IProfileService {
  private final AccountRepository accountRepository;
  private final EmployerProfileRepository employerProfileRepository;
  private final DistrictServiceImpl districtService;
  private final SocialLinkRepository socialLinkRepository;
  private final ProvinceServiceImpl provinceService;
  private final IFileService cloudinaryService;

  @Value("${default-employer-logo}")
  private String defaultEmployerLogo;

  @Autowired
  public EmployerProfileServiceImpl(
      AccountRepository accountRepository,
      EmployerProfileRepository employerProfileRepository,
      DistrictServiceImpl districtService,
      SocialLinkRepository socialLinkRepository,
      ProvinceServiceImpl provinceService,
      IFileService cloudinaryService) {
    this.accountRepository = accountRepository;
    this.employerProfileRepository = employerProfileRepository;
    this.districtService = districtService;
    this.socialLinkRepository = socialLinkRepository;
    this.provinceService = provinceService;
    this.cloudinaryService = cloudinaryService;
  }

  @Override
  @Transactional
  public BaseProfile createProfile(String accountId, BaseProfile profileRequest) {
    EmployerProfileRequest employerProfileRequest = (EmployerProfileRequest) profileRequest;
    if (employerProfileRepository.existsByAccountId(UUID.fromString(accountId)))
      throw new ForbiddenException(MessageConstants.PROFILE_ALREADY_EXISTS);
    Account account =
        accountRepository
            .findById(UUID.fromString(accountId))
            .orElseThrow(() -> new NotFoundException(MessageConstants.ACCOUNT_NOT_FOUND));
    Province province = provinceService.getProvinceByCode(employerProfileRequest.getProvinceCode());
    District district =
        districtService.getDistrictByCodeAndProvinceCode(
            employerProfileRequest.getDistrictCode(), employerProfileRequest.getProvinceCode());
    EmployerProfile employerProfile =
        EmployerProfile.builder()
            .account(account)
            .name(employerProfileRequest.getName())
            .about(employerProfileRequest.getAbout())
            .establishedIn(employerProfileRequest.getEstablishedIn())
            .vision(employerProfileRequest.getVision())
            .logo_url(defaultEmployerLogo)
            .province(province)
            .website_url(employerProfileRequest.getWebsiteUrl())
            .district(district)
            .socialLinks(new ArrayList<>())
            .build();

    employerProfile = employerProfileRepository.save(employerProfile);

    account.setEmployerProfile(employerProfile);
    accountRepository.save(account);

    return new EmployerProfileDto(employerProfile);
  }

  @Override
  public BaseProfile getProfile(String employerId) {
    EmployerProfile employerProfile =
        employerProfileRepository
            .findById(UUID.fromString(employerId))
            .orElseThrow(() -> new NotFoundException(MessageConstants.PROFILE_NOT_FOUND));
    return new EmployerProfileDto(employerProfile);
  }

  @Override
  @Transactional
  public void updateSocialLinks(String accountId, List<SocialLinkDto> socialLinkDtos) {
    EmployerProfile employerProfile =
        employerProfileRepository
            .findByAccountId(UUID.fromString(accountId))
            .orElseThrow(() -> new NotFoundException(MessageConstants.PROFILE_NOT_FOUND));
    socialLinkRepository.deleteByEmployerProfile(employerProfile);
    List<SocialLink> socialLinkList =
        socialLinkDtos.stream()
            .map(
                socialLinkDto ->
                    new SocialLink(
                        socialLinkDto.getType(), socialLinkDto.getUrl(), null, employerProfile))
            .toList();
    socialLinkRepository.saveAll(socialLinkList);
  }

  @Override
  @Transactional
  public void updateProfile(String accountId, BaseProfile profileRequest) {
    EmployerProfile employerProfile =
        employerProfileRepository
            .findByAccountId(UUID.fromString(accountId))
            .orElseThrow(() -> new NotFoundException(MessageConstants.PROFILE_NOT_FOUND));
    EmployerProfileRequest employerProfileRequest = (EmployerProfileRequest) profileRequest;
    Province province = provinceService.getProvinceByCode(employerProfileRequest.getProvinceCode());
    District district =
        districtService.getDistrictByCodeAndProvinceCode(
            employerProfileRequest.getDistrictCode(), employerProfileRequest.getProvinceCode());
    employerProfile.update(employerProfileRequest);
    employerProfile.setProvince(province);
    employerProfile.setDistrict(district);
    employerProfileRepository.save(employerProfile);
  }

  @Override
  @Transactional
  public void updateProfileImage(String accountId, MultipartFile image) {
    try {
      String imgUrl = cloudinaryService.uploadImage(image);
      EmployerProfile employerProfile =
          employerProfileRepository
              .findByAccountId(UUID.fromString(accountId))
              .orElseThrow(() -> new NotFoundException(MessageConstants.PROFILE_NOT_FOUND));
      employerProfile.setLogo_url(imgUrl);
      employerProfileRepository.save(employerProfile);
    } catch (Exception e) {
      throw new BadRequestException(MessageConstants.IMAGE_UPLOAD_FAILED);
    }
  }
}
