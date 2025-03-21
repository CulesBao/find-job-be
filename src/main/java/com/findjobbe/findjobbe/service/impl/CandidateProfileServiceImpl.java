package com.findjobbe.findjobbe.service.impl;

import com.findjobbe.findjobbe.exception.MessageConstants;
import com.findjobbe.findjobbe.exception.NotFoundException;
import com.findjobbe.findjobbe.mapper.dto.BaseProfile;
import com.findjobbe.findjobbe.mapper.dto.CandidateProfileDto;
import com.findjobbe.findjobbe.mapper.request.CandidateProfileRequest;
import com.findjobbe.findjobbe.model.Account;
import com.findjobbe.findjobbe.model.CandidateProfile;
import com.findjobbe.findjobbe.repository.AccountRepository;
import com.findjobbe.findjobbe.repository.CandidateProfileRepository;
import com.findjobbe.findjobbe.service.IProfileService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidateProfileServiceImpl implements IProfileService {
  private final AccountRepository accountRepository;
  private final CandidateProfileRepository candidateProfileRepository;

  @Autowired
  public CandidateProfileServiceImpl(
      AccountRepository accountRepository, CandidateProfileRepository candidateProfileRepository) {
    this.accountRepository = accountRepository;
    this.candidateProfileRepository = candidateProfileRepository;
  }

  @Override
  public BaseProfile createProfile(UUID id, BaseProfile profileRequest) {
    CandidateProfileRequest candidateProfileRequest = (CandidateProfileRequest) profileRequest;
    Account account =
        accountRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException(MessageConstants.ACCOUNT_NOT_FOUND));
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
            .account(account)
            .build();

    candidateProfile = candidateProfileRepository.save(candidateProfile);

    account.setCandidateProfile(candidateProfile);
    accountRepository.save(account);

    return new CandidateProfileDto(candidateProfile);
  }
}
