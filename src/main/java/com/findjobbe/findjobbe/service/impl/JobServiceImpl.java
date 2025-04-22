package com.findjobbe.findjobbe.service.impl;

import com.findjobbe.findjobbe.exception.ForbiddenException;
import com.findjobbe.findjobbe.exception.MessageConstants;
import com.findjobbe.findjobbe.exception.NotFoundException;
import com.findjobbe.findjobbe.mapper.dto.EmployerJobsDto;
import com.findjobbe.findjobbe.mapper.dto.FilterJobsDto;
import com.findjobbe.findjobbe.mapper.request.CreateJobRequest;
import com.findjobbe.findjobbe.mapper.request.FilterJobRequest;
import com.findjobbe.findjobbe.model.EmployerProfile;
import com.findjobbe.findjobbe.model.Job;
import com.findjobbe.findjobbe.repository.EmployerProfileRepository;
import com.findjobbe.findjobbe.repository.JobRepository;
import com.findjobbe.findjobbe.service.IJobService;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class JobServiceImpl implements IJobService {
  private final EmployerProfileRepository employerProfileRepository;
  private final JobRepository jobRepository;

  @Autowired
  public JobServiceImpl(
      EmployerProfileRepository employerProfileRepository, JobRepository jobRepository) {
    this.employerProfileRepository = employerProfileRepository;
    this.jobRepository = jobRepository;
  }

  @Override
  public void createJob(String employerId, CreateJobRequest createJobRequest) {
    if (createJobRequest.getMinSalary() >= createJobRequest.getMaxSalary()) {
      throw new ForbiddenException(MessageConstants.MIN_SALARY_MUST_BE_LESS_THAN_MAX_SALARY);
    }
    if (createJobRequest.getExpiredAt() != null
        && createJobRequest.getExpiredAt().before(new Date())) {
      throw new ForbiddenException(MessageConstants.EXPIRED_AT_MUST_BE_IN_FUTURE);
    }
    EmployerProfile employerProfile =
        employerProfileRepository
            .findById(UUID.fromString(employerId))
            .orElseThrow(() -> new NotFoundException(MessageConstants.EMPLOYER_NOT_FOUND));
    Job job = new Job(createJobRequest, employerProfile);
    jobRepository.save(job);
  }

  @Override
  public Job getJobById(String jobId) {
    return jobRepository
        .findById(UUID.fromString(jobId))
        .orElseThrow(() -> new NotFoundException(MessageConstants.JOB_NOT_FOUND));
  }

  @Override
  public void updateJob(String employerId, String jobId, CreateJobRequest createJobRequest) {
    Job job = getJobById(jobId);
    if (createJobRequest.getMinSalary() >= createJobRequest.getMaxSalary()) {
      throw new ForbiddenException(MessageConstants.MIN_SALARY_MUST_BE_LESS_THAN_MAX_SALARY);
    }
    if (createJobRequest.getExpiredAt() != null
        && createJobRequest.getExpiredAt().before(new Date())) {
      throw new ForbiddenException(MessageConstants.EXPIRED_AT_MUST_BE_IN_FUTURE);
    }
    if (!job.getEmployerProfile().getId().toString().equals(employerId))
      throw new ForbiddenException(MessageConstants.NOT_AUTHORIZED_TO_UPDATE_JOB);
    job.update(createJobRequest);
    jobRepository.save(job);
  }

  @Override
  public Page<EmployerJobsDto[]> getEmployerJobs(String employerId, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return jobRepository.getAllEmployerJobsRaw(UUID.fromString(employerId), pageable);
  }

  @Override
  public Page<FilterJobsDto[]> filterJobs(FilterJobRequest filterJobRequest, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return jobRepository.filterJobs(
        filterJobRequest.getTitle(),
        filterJobRequest.getProvinceCode(),
        filterJobRequest.getJobType(),
        filterJobRequest.getEducation(),
        filterJobRequest.getMinSalary(),
        filterJobRequest.getMaxSalary(),
        filterJobRequest.getCurrency(),
        filterJobRequest.getSalaryType(),
        pageable);
  }

  @Override
  public void deleteJobById(String employerId, String jobId) {
    Job job = getJobById(jobId);
    if (!job.getEmployerProfile().getId().toString().equals(employerId))
      throw new ForbiddenException(MessageConstants.NOT_AUTHORIZED_TO_DELETE_JOB);
    jobRepository.delete(job);
  }
}
