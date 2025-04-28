package com.findjobbe.findjobbe.controller;

import com.findjobbe.findjobbe.config.CurrentUser;
import com.findjobbe.findjobbe.enums.Role;
import com.findjobbe.findjobbe.factory.ProfileServiceFactory;
import com.findjobbe.findjobbe.mapper.dto.CandidateProfileDto;
import com.findjobbe.findjobbe.mapper.request.CandidateProfileRequest;
import com.findjobbe.findjobbe.mapper.request.FilterCandidateRequest;
import com.findjobbe.findjobbe.mapper.request.SocialLinkRequest;
import com.findjobbe.findjobbe.mapper.response.AbstractResponse;
import com.findjobbe.findjobbe.model.CustomAccountDetails;
import com.findjobbe.findjobbe.service.IProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/candidate-profile")
public class CandidateProfileController {
  private final IProfileService profileService;

  @Autowired
  public CandidateProfileController(ProfileServiceFactory profileServiceFactory) {
    this.profileService = profileServiceFactory.getProfileService(Role.CANDIDATE);
  }

  @PostMapping("/")
  @PreAuthorize("hasRole('ROLE_CANDIDATE')")
  public ResponseEntity<?> createProfile(
      @Valid @RequestBody CandidateProfileRequest request,
      @CurrentUser CustomAccountDetails currentUser) {
    CandidateProfileDto candidateProfile =
        (CandidateProfileDto)
            profileService.createProfile(currentUser.getAccount().getId().toString(), request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new AbstractResponse("Profile created successfully", candidateProfile));
  }

  @GetMapping("/{candidateProfileId}")
  public ResponseEntity<?> getProfile(@PathVariable String candidateProfileId) {
    CandidateProfileDto candidateProfile =
        (CandidateProfileDto) profileService.getProfile(candidateProfileId);
    return ResponseEntity.ok(
        new AbstractResponse("Profile retrieved successfully", candidateProfile));
  }

  @PutMapping("/social-links")
  @PreAuthorize("hasRole('ROLE_CANDIDATE')")
  public ResponseEntity<?> updateSocialLinks(
      @Valid @RequestBody SocialLinkRequest request,
      @CurrentUser CustomAccountDetails currentUser) {
    profileService.updateSocialLinks(
        currentUser.getAccount().getId().toString(), request.getSocialLinks());
    return ResponseEntity.ok(new AbstractResponse("Social links updated successfully", null));
  }

  @PutMapping("/")
  @PreAuthorize("hasRole('ROLE_CANDIDATE')")
  public ResponseEntity<?> updateProfile(
      @Valid @RequestBody CandidateProfileRequest request,
      @CurrentUser CustomAccountDetails currentUser) {
    profileService.updateProfile(currentUser.getAccount().getId().toString(), request);
    return ResponseEntity.ok(new AbstractResponse("Profile updated successfully", null));
  }

  @PutMapping("/update-avatar")
  @PreAuthorize("hasRole('ROLE_CANDIDATE')")
  public ResponseEntity<?> updateAvatar(
      @RequestParam("avatar") MultipartFile avatar, @CurrentUser CustomAccountDetails currentUser) {
    profileService.updateProfileImage(currentUser.getAccount().getId().toString(), avatar);
    return ResponseEntity.ok(new AbstractResponse("Avatar updated successfully", null));
  }

  @GetMapping("/filter")
  public ResponseEntity<?> filterCandidate(
      @RequestParam(value = "firstName", required = false) String firstName,
      @RequestParam(value = "lastName", required = false) String lastName,
      @RequestParam(value = "provinceCode", required = false) String provinceCode,
      @RequestParam(value = "education", required = false) String education,
      @RequestParam(value = "gender", required = false) String gender,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size) {
    return ResponseEntity.ok(
        new AbstractResponse(
            "Filtered candidates successfully",
            profileService.filterProfiles(
                new FilterCandidateRequest(firstName, lastName, provinceCode, education, gender),
                page,
                size)));
  }
}
