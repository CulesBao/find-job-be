package com.findjobbe.findjobbe.controller;

import com.findjobbe.findjobbe.config.CurrentUser;
import com.findjobbe.findjobbe.enums.Role;
import com.findjobbe.findjobbe.factory.FollowerServiceFactory;
import com.findjobbe.findjobbe.mapper.response.AbstractResponse;
import com.findjobbe.findjobbe.model.CustomAccountDetails;
import com.findjobbe.findjobbe.service.IFollowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/candidates/followers")
public class CandidateFollowerController {
  private final IFollowerService followerService;

  @Autowired
  public CandidateFollowerController(FollowerServiceFactory factory) {
    this.followerService = factory.getFollowerService(Role.CANDIDATE);
  }

  @PutMapping("/{employerId}")
  @PreAuthorize("hasRole('ROLE_CANDIDATE')")
  public ResponseEntity<?> savedEmployer(
      @PathVariable String employerId, @CurrentUser CustomAccountDetails currentUser) {
    followerService.savedFollow(
        currentUser.getAccount().getCandidateProfile().getId().toString(), employerId);
    return ResponseEntity.ok(new AbstractResponse("Followed successfully", null));
  }

  @DeleteMapping("/{employerId}")
  @PreAuthorize("hasRole('ROLE_CANDIDATE')")
  public ResponseEntity<?> unSavedEmployer(
      @PathVariable String employerId, @CurrentUser CustomAccountDetails currentUser) {
    followerService.deleteFollow(
        currentUser.getAccount().getCandidateProfile().getId().toString(), employerId);
    return ResponseEntity.ok(new AbstractResponse("Unfollowed successfully", null));
  }

  @GetMapping("/")
  @PreAuthorize("hasRole('ROLE_CANDIDATE')")
  public ResponseEntity<?> getAllSavedEmployers(
      @CurrentUser CustomAccountDetails currentUser,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size) {
    return ResponseEntity.ok(
        new AbstractResponse(
            "Get all saved employers successfully",
            followerService.getFollowers(
                currentUser.getAccount().getCandidateProfile().getId().toString(), page, size)));
  }
}
