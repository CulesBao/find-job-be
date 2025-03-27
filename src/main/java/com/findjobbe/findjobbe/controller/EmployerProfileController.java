package com.findjobbe.findjobbe.controller;

import com.findjobbe.findjobbe.config.CurrentUser;
import com.findjobbe.findjobbe.enums.Role;
import com.findjobbe.findjobbe.factory.ProfileServiceFactory;
import com.findjobbe.findjobbe.mapper.dto.EmployerProfileDto;
import com.findjobbe.findjobbe.mapper.request.EmployerProfileRequest;
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

@RequestMapping("/api/employer-profile")
@RestController
public class EmployerProfileController {
  private final IProfileService profileService;

  @Autowired
  public EmployerProfileController(ProfileServiceFactory profileServiceFactory) {
    this.profileService = profileServiceFactory.getProfileService(Role.EMPLOYER);
  }

  @PostMapping("/")
  @PreAuthorize("hasRole('ROLE_EMPLOYER')")
  public ResponseEntity<?> createProfile(
      @Valid @RequestBody EmployerProfileRequest employerProfileRequest,
      @CurrentUser CustomAccountDetails currentUser) {
    EmployerProfileDto employerProfileDto =
        (EmployerProfileDto)
            profileService.createProfile(
                currentUser.getAccount().getId().toString(), employerProfileRequest);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new AbstractResponse("Profile created successfully", employerProfileDto));
  }

  @GetMapping("/{employerId}")
  public ResponseEntity<?> getProfile(@PathVariable String employerId) {
    EmployerProfileDto employerProfileDto =
        (EmployerProfileDto) profileService.getProfile(employerId);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new AbstractResponse("Profile retrieved successfully", employerProfileDto));
  }

  @PutMapping("/")
  @PreAuthorize("hasRole('ROLE_EMPLOYER')")
  public ResponseEntity<?> updateProfile(
      @Valid @RequestBody EmployerProfileRequest employerProfileRequest,
      @CurrentUser CustomAccountDetails currentUser) {
    profileService.updateProfile(
        currentUser.getAccount().getId().toString(), employerProfileRequest);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new AbstractResponse("Profile updated successfully", null));
  }

  @PutMapping("/social-links")
  @PreAuthorize("hasRole('ROLE_EMPLOYER')")
  public ResponseEntity<?> updateSocialLinks(
      @Valid @RequestBody SocialLinkRequest request,
      @CurrentUser CustomAccountDetails currentUser) {
    profileService.updateSocialLinks(
        currentUser.getAccount().getId().toString(), request.getSocialLinks());
    return ResponseEntity.ok(new AbstractResponse("Social links updated successfully", null));
  }

  @PutMapping("/update-logo")
  @PreAuthorize("hasRole('ROLE_EMPLOYER')")
  public ResponseEntity<?> updateLogo(
      @RequestParam("logo") MultipartFile logo, @CurrentUser CustomAccountDetails currentUser) {
    profileService.updateProfileImage(currentUser.getAccount().getId().toString(), logo);
    return ResponseEntity.ok(new AbstractResponse("Logo updated successfully", null));
  }
}
