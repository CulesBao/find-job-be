package com.findjobbe.findjobbe.controller;

import com.findjobbe.findjobbe.config.CurrentUser;
import com.findjobbe.findjobbe.factory.ProfileServiceFactory;
import com.findjobbe.findjobbe.mapper.dto.CandidateProfileDto;
import com.findjobbe.findjobbe.mapper.request.CandidateProfileRequest;
import com.findjobbe.findjobbe.mapper.response.AbstractResponse;
import com.findjobbe.findjobbe.model.CustomAccountDetails;
import com.findjobbe.findjobbe.service.IProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/candidate-profile")
public class CandidateProfileController {
  private final ProfileServiceFactory profileServiceFactory;

  @Autowired
  public CandidateProfileController(ProfileServiceFactory profileServiceFactory) {
    this.profileServiceFactory = profileServiceFactory;
  }

  @PostMapping("/")
  @PreAuthorize("hasRole('ROLE_CANDIDATE')")
  public ResponseEntity<?> createProfile(
      @Valid @RequestBody CandidateProfileRequest request,
      @CurrentUser CustomAccountDetails currentUser) {
    IProfileService profileService =
        this.profileServiceFactory.getProfileService(currentUser.getAccount().getRole());
    CandidateProfileDto candidateProfile =
        (CandidateProfileDto)
            profileService.createProfile(currentUser.getAccount().getId(), request);
    return ResponseEntity.ok(
        new AbstractResponse("Profile created successfully", candidateProfile));
  }
}
