package com.findjobbe.findjobbe.controller;

import com.findjobbe.findjobbe.config.CurrentUser;
import com.findjobbe.findjobbe.mapper.dto.JobDto;
import com.findjobbe.findjobbe.mapper.request.CreateJobRequest;
import com.findjobbe.findjobbe.mapper.request.FilterJobRequest;
import com.findjobbe.findjobbe.mapper.response.AbstractResponse;
import com.findjobbe.findjobbe.model.CustomAccountDetails;
import com.findjobbe.findjobbe.service.IJobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/job")
public class JobController {
  private final IJobService jobService;

  @Autowired
  public JobController(IJobService jobService) {
    this.jobService = jobService;
  }

  @PostMapping("/")
  @PreAuthorize("hasRole('ROLE_EMPLOYER')")
  public ResponseEntity<?> createJob(
      @CurrentUser CustomAccountDetails customAccountDetails,
      @RequestBody @Valid CreateJobRequest createJobRequest) {
    jobService.createJob(
        customAccountDetails.getAccount().getEmployerProfile().getId().toString(),
        createJobRequest);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new AbstractResponse("Create job successfully", null));
  }

  @GetMapping("/{jobId}")
  public ResponseEntity<?> getJobById(@PathVariable String jobId) {
    return ResponseEntity.ok(
        new AbstractResponse("Get job successfully", new JobDto(jobService.getJobById(jobId))));
  }

  @PutMapping("/{jobId}")
  @PreAuthorize("hasRole('ROLE_EMPLOYER')")
  public ResponseEntity<?> updateJob(
      @PathVariable String jobId,
      @RequestBody @Valid CreateJobRequest updateJobResquest,
      @CurrentUser CustomAccountDetails currentUser) {
    jobService.updateJob(
        currentUser.getAccount().getEmployerProfile().getId().toString(), jobId, updateJobResquest);
    return ResponseEntity.ok(new AbstractResponse("Update job successfully", null));
  }

  @GetMapping("/")
  @PreAuthorize("hasRole('ROLE_EMPLOYER')")
  public ResponseEntity<?> getEmployerJobs(
      @CurrentUser CustomAccountDetails customAccountDetails,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    return ResponseEntity.ok(
        new AbstractResponse(
            "Get employer jobs successfully",
            jobService.getEmployerJobs(
                customAccountDetails.getAccount().getEmployerProfile().getId().toString(),
                page,
                size)));
  }

  @PostMapping("/filter")
  public ResponseEntity<?> filterJobs(
      @RequestBody @Valid FilterJobRequest filterJobRequest,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    return ResponseEntity.ok(
        new AbstractResponse(
            "Filter jobs successfully", jobService.filterJobs(filterJobRequest, page, size)));
  }

  @DeleteMapping("/{jobId}")
  @PreAuthorize("hasRole('ROLE_EMPLOYER')")
  public ResponseEntity<?> deleteJobById(
      @PathVariable String jobId, @CurrentUser CustomAccountDetails currentUser) {
    jobService.deleteJobById(
        currentUser.getAccount().getEmployerProfile().getId().toString(), jobId);
    return ResponseEntity.ok(new AbstractResponse("Delete job successfully", null));
  }
}
