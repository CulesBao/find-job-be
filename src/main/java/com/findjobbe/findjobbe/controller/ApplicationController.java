package com.findjobbe.findjobbe.controller;

import com.findjobbe.findjobbe.config.CurrentUser;
import com.findjobbe.findjobbe.exception.ForbiddenException;
import com.findjobbe.findjobbe.mapper.request.SetApplicationsStatusRequest;
import com.findjobbe.findjobbe.mapper.response.AbstractResponse;
import com.findjobbe.findjobbe.model.CustomAccountDetails;
import com.findjobbe.findjobbe.service.IApplicationService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/application")
public class ApplicationController {
  private final IApplicationService applicationService;

  @Autowired
  public ApplicationController(IApplicationService applicationService) {
    this.applicationService = applicationService;
  }

  @PostMapping("/")
  @PreAuthorize("hasRole('ROLE_CANDIDATE')")
  public ResponseEntity<?> applyForJob(
      @RequestParam("file") MultipartFile file,
      @RequestParam("cover_letter") String coverLetter,
      @RequestParam("job_id") String jobId,
      @CurrentUser CustomAccountDetails currentUser)
      throws ForbiddenException, IOException {
    applicationService.applyForJob(
        jobId,
        currentUser.getAccount().getCandidateProfile().getId().toString(),
        coverLetter,
        file);
    return ResponseEntity.ok(new AbstractResponse("Application submitted successfully", null));
  }

  @PutMapping("/{applicationId}/withdraw")
  @PreAuthorize("hasRole('ROLE_CANDIDATE')")
  public ResponseEntity<?> withdrawApplication(
      @PathVariable String applicationId, @CurrentUser CustomAccountDetails currentUser) {
    applicationService.withdrawApplication(
        currentUser.getAccount().getCandidateProfile().getId().toString(), applicationId);
    return ResponseEntity.ok(new AbstractResponse("Application withdrawn successfully", null));
  }

  @GetMapping("/")
  @PreAuthorize("hasRole('ROLE_CANDIDATE')")
  public ResponseEntity<?> getAllApplications(
      @CurrentUser CustomAccountDetails currentUser,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size) {
    return ResponseEntity.ok(
        new AbstractResponse(
            "Get all applications successfully",
            applicationService.getCandidateAppliedJobs(
                currentUser.getAccount().getCandidateProfile().getId().toString(), page, size)));
  }

  @GetMapping("/job/{jobId}")
  @PreAuthorize("hasRole('ROLE_EMPLOYER')")
  public ResponseEntity<?> getAllApplicationsByJobId(
      @PathVariable String jobId,
      @CurrentUser CustomAccountDetails currentUser,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size) {
    return ResponseEntity.ok(
        new AbstractResponse(
            "Get all applications successfully",
            applicationService.getEmployerAppliedJobs(
                currentUser.getAccount().getEmployerProfile().getId().toString(),
                jobId,
                page,
                size)));
  }

  @GetMapping("/{applicationId}")
  @PreAuthorize("hasRole('ROLE_EMPLOYER') or hasRole('ROLE_CANDIDATE')")
  public ResponseEntity<?> getApplicationById(
      @CurrentUser CustomAccountDetails currentUser,
      @PathVariable("applicationId") String applicationId) {
    return ResponseEntity.ok(
        new AbstractResponse(
            "Get application successfully",
            applicationService.getApplicationById(applicationId, currentUser)));
  }

  @PutMapping("/job/{jobId}")
  @PreAuthorize("hasRole('ROLE_EMPLOYER')")
  public ResponseEntity<?> updateApplicationStatus(
      @PathVariable String jobId,
      @RequestBody SetApplicationsStatusRequest setApplicationsStatusRequest,
      @CurrentUser CustomAccountDetails currentUser) {
    applicationService.updateApplicationStatus(
        setApplicationsStatusRequest,
        currentUser.getAccount().getEmployerProfile().getId().toString(),
        jobId);
    return ResponseEntity.ok(new AbstractResponse("Update application status successfully", null));
  }

  @GetMapping("/job/{jobId}/status")
  @PreAuthorize("hasRole('ROLE_CANDIDATE')")
  public ResponseEntity<?> isCandidateApplied(
      @PathVariable String jobId, @CurrentUser CustomAccountDetails currentUser) {
    return ResponseEntity.ok(
        new AbstractResponse(
            "Check application status successfully",
            applicationService.isCandidateApplied(
                currentUser.getAccount().getCandidateProfile().getId().toString(), jobId)));
  }
}
