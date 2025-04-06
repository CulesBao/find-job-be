package com.findjobbe.findjobbe.controller;

import com.findjobbe.findjobbe.config.CurrentUser;
import com.findjobbe.findjobbe.enums.Role;
import com.findjobbe.findjobbe.factory.FollowerServiceFactory;
import com.findjobbe.findjobbe.mapper.response.AbstractResponse;
import com.findjobbe.findjobbe.model.CustomAccountDetails;
import com.findjobbe.findjobbe.service.IFollowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employers/followers")
public class EmployerFollowerController {
  private final IFollowerService followerService;

  @Autowired
  public EmployerFollowerController(FollowerServiceFactory factory) {
    this.followerService = factory.getFollowerService(Role.EMPLOYER);
  }

  @PutMapping("/{candidateId}")
  public ResponseEntity<?> savedCandidate(
      @PathVariable String candidateId, @CurrentUser CustomAccountDetails currentUser) {
    followerService.savedFollow(
        currentUser.getAccount().getEmployerProfile().getId().toString(), candidateId);
    return ResponseEntity.ok(new AbstractResponse("Followed successfully", null));
  }

  @DeleteMapping("/{candidateId}")
  public ResponseEntity<?> unSavedCandidate(
      @PathVariable String candidateId, @CurrentUser CustomAccountDetails currentUser) {
    followerService.deleteFollow(
        currentUser.getAccount().getEmployerProfile().getId().toString(), candidateId);
    return ResponseEntity.ok(new AbstractResponse("Unfollowed successfully", null));
  }

  @GetMapping("/")
  public ResponseEntity<?> getAllSavedCandidates(
      @CurrentUser CustomAccountDetails currentUser,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size) {
    return ResponseEntity.ok(
        new AbstractResponse(
            "Get all saved candidates successfully",
            followerService.getFollowers(
                currentUser.getAccount().getEmployerProfile().getId().toString(), page, size)));
  }
}
