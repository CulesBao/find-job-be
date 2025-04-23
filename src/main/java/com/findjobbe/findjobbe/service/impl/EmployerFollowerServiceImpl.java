package com.findjobbe.findjobbe.service.impl;

import com.findjobbe.findjobbe.exception.MessageConstants;
import com.findjobbe.findjobbe.exception.NotFoundException;
import com.findjobbe.findjobbe.model.CandidateProfile;
import com.findjobbe.findjobbe.model.EmployerProfile;
import com.findjobbe.findjobbe.repository.CandidateProfileRepository;
import com.findjobbe.findjobbe.repository.EmployerProfileRepository;
import com.findjobbe.findjobbe.service.IFollowerService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployerFollowerServiceImpl implements IFollowerService {
  private final CandidateProfileRepository candidateProfileRepository;
  private final EmployerProfileRepository employerProfileRepository;

  @Autowired
  public EmployerFollowerServiceImpl(
      CandidateProfileRepository candidateProfileRepository,
      EmployerProfileRepository employerProfileRepository) {
    this.candidateProfileRepository = candidateProfileRepository;
    this.employerProfileRepository = employerProfileRepository;
  }

  CandidateProfile findCandidateById(String id) {
    return candidateProfileRepository
        .findById(UUID.fromString(id))
        .orElseThrow(() -> new NotFoundException(MessageConstants.PROFILE_NOT_FOUND));
  }

  EmployerProfile findEmployerById(String id) {
    return employerProfileRepository
        .findById(UUID.fromString(id))
        .orElseThrow(() -> new NotFoundException(MessageConstants.PROFILE_NOT_FOUND));
  }

  @Override
  public void savedFollow(String employerProfileId, String candidateProfileId) {
    CandidateProfile candidateProfile = findCandidateById(candidateProfileId);
    EmployerProfile employerProfile = findEmployerById(employerProfileId);

    if (employerProfile.getSavedCandidates().contains(candidateProfile)) {
      throw new NotFoundException(MessageConstants.EMPLOYER_ALREADY_FOLLOWING);
    }

    employerProfile.getSavedCandidates().add(candidateProfile);
    employerProfileRepository.save(employerProfile);
  }

  @Override
  public void deleteFollow(String employerProfileId, String candidateProfileId) {
    CandidateProfile candidateProfile = findCandidateById(candidateProfileId);
    EmployerProfile employerProfile = findEmployerById(employerProfileId);

    if (!employerProfile.getSavedCandidates().contains(candidateProfile)) {
      throw new NotFoundException(MessageConstants.EMPLOYER_NOT_FOLLOWING);
    }

    employerProfile.getSavedCandidates().remove(candidateProfile);
    employerProfileRepository.save(employerProfile);
  }

  @Override
  public Page<?> getFollowers(String employerProfileId, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return candidateProfileRepository.findAllByEmployerId(
        UUID.fromString(employerProfileId), pageable);
  }

  @Override
  public boolean isFollowed(String followerId, String followingId){
    CandidateProfile candidateProfile = findCandidateById(followingId);
    EmployerProfile employerProfile = findEmployerById(followerId);

    return employerProfile.getSavedCandidates().contains(candidateProfile);
  }
}
