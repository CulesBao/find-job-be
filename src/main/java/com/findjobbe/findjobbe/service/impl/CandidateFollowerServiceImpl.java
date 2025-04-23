package com.findjobbe.findjobbe.service.impl;

import com.findjobbe.findjobbe.exception.BadRequestException;
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
public class CandidateFollowerServiceImpl implements IFollowerService {
  private final CandidateProfileRepository candidateProfileRepository;
  private final EmployerProfileRepository employerProfileRepository;

  @Autowired
  public CandidateFollowerServiceImpl(
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
  public void savedFollow(String candidateProfileId, String employerProfileId) {
    CandidateProfile candidateProfile = findCandidateById(candidateProfileId);
    EmployerProfile employerProfile = findEmployerById(employerProfileId);
    if (candidateProfile.getSavedEmployers().contains(employerProfile)) {
      throw new BadRequestException(MessageConstants.CANDIDATE_ALREADY_FOLLOWING);
    }
    candidateProfile.getSavedEmployers().add(employerProfile);
    candidateProfileRepository.save(candidateProfile);
  }

  @Override
  public void deleteFollow(String candidateProfileId, String employerProfileId) {
    CandidateProfile candidateProfile = findCandidateById(candidateProfileId);
    EmployerProfile employerProfile = findEmployerById(employerProfileId);
    if (!candidateProfile.getSavedEmployers().contains(employerProfile)) {
      throw new BadRequestException(MessageConstants.CANDIDATE_NOT_FOLLOWING);
    }

    candidateProfile.getSavedEmployers().remove(employerProfile);
    candidateProfileRepository.save(candidateProfile);
  }

  @Override
  public Page<?> getFollowers(String candidateProfileId, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return employerProfileRepository.findAllByCandidateId(
        UUID.fromString(candidateProfileId), pageable);
  }

  @Override
  public boolean isFollowed(String followerId, String followingId) {
    CandidateProfile candidateProfile = findCandidateById(followerId);
    EmployerProfile employerProfile = findEmployerById(followingId);
    if (candidateProfile.getSavedEmployers().contains(employerProfile)) {
      return true;
    }
    return false;
  }
}
