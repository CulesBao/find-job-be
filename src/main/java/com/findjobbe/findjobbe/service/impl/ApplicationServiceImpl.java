package com.findjobbe.findjobbe.service.impl;

import com.findjobbe.findjobbe.enums.JobProccess;
import com.findjobbe.findjobbe.exception.ForbiddenException;
import com.findjobbe.findjobbe.exception.MessageConstants;
import com.findjobbe.findjobbe.exception.NotFoundException;
import com.findjobbe.findjobbe.mapper.dto.ApplicationDto;
import com.findjobbe.findjobbe.mapper.dto.CandidateProfileDto;
import com.findjobbe.findjobbe.mapper.dto.GetCandidateAppliedJobs;
import com.findjobbe.findjobbe.mapper.dto.GetEmployerAppliedJobs;
import com.findjobbe.findjobbe.model.Application;
import com.findjobbe.findjobbe.model.CandidateProfile;
import com.findjobbe.findjobbe.model.CustomAccountDetails;
import com.findjobbe.findjobbe.repository.ApplicationRepository;
import com.findjobbe.findjobbe.repository.CandidateProfileRepository;
import com.findjobbe.findjobbe.service.IApplicationService;
import com.findjobbe.findjobbe.service.IFileService;
import com.findjobbe.findjobbe.service.IJobService;
import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ApplicationServiceImpl implements IApplicationService {
  private final IJobService jobService;
  private final CandidateProfileRepository candidateProfileRepository;
  private final ApplicationRepository applicationRepository;
  private final IFileService fileService;

  @Autowired
  public ApplicationServiceImpl(
      IJobService jobService,
      CandidateProfileRepository candidateProfileRepository,
      ApplicationRepository applicationRepository,
      IFileService fileService) {
    this.jobService = jobService;
    this.candidateProfileRepository = candidateProfileRepository;
    this.applicationRepository = applicationRepository;
    this.fileService = fileService;
  }

  @Override
  public void applyForJob(String jobId, String candidateId, String coverLetter, MultipartFile file)
      throws IOException, ForbiddenException {
    CandidateProfile candidateProfile =
        candidateProfileRepository
            .findById(UUID.fromString(candidateId))
            .orElseThrow(() -> new NotFoundException(MessageConstants.PROFILE_NOT_FOUND));
    Application application =
        new Application(
            fileService.uploadFile(file),
            coverLetter,
            false,
            candidateProfile,
            jobService.getJobById(jobId.toString()),
            JobProccess.APPLICATION_SUBMITTED);
    applicationRepository.save(application);
  }

  @Override
  public void withdrawApplication(String candidateId, String applicationId) {
    Application application =
        applicationRepository
            .findById(UUID.fromString(applicationId))
            .orElseThrow(() -> new NotFoundException(MessageConstants.APPLICATION_NOT_FOUND));
    if (!application.getCandidateProfile().getId().equals(UUID.fromString(candidateId))) {
      throw new ForbiddenException(MessageConstants.UNAUTHORIZED_ACCESS);
    }
    if (application.getProccess() == JobProccess.WITHDRAWN) {
      throw new ForbiddenException(MessageConstants.APPLICATION_ALREADY_WITHDRAWN);
    }
    application.setProccess(JobProccess.WITHDRAWN);
    applicationRepository.save(application);
  }

  @Override
  public Page<GetCandidateAppliedJobs[]> getCandidateAppliedJobs(
      String candidateId, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return applicationRepository.findAllByCandidateProfileId(
        UUID.fromString(candidateId), pageable);
  }

  @Override
  public Page<GetEmployerAppliedJobs[]> getEmployerAppliedJobs(
      String employerId, String jobId, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return applicationRepository.getEmployerAppliedJobs(
        UUID.fromString(employerId), UUID.fromString(jobId), pageable);
  }

  @Override
  public ApplicationDto getApplicationById(
      String applicationId, CustomAccountDetails customAccountDetails) {
    Application application =
        applicationRepository
            .findById(UUID.fromString(applicationId))
            .orElseThrow(() -> new NotFoundException(MessageConstants.APPLICATION_NOT_FOUND));
    CandidateProfileDto candidateProfileDto =
        new CandidateProfileDto(application.getCandidateProfile());
    return new ApplicationDto(
        application.getId(),
        application.getCoverLetter(),
        application.getCvUrl(),
        application.getProccess(),
        candidateProfileDto,
        application.getCreatedAt(),
        application.getUpdatedAt());
  }
}
