package com.findjobbe.findjobbe.service;

import com.findjobbe.findjobbe.mapper.dto.GetEmployerJobsDto;
import com.findjobbe.findjobbe.mapper.request.CreateJobRequest;
import com.findjobbe.findjobbe.model.Job;
import org.springframework.data.domain.Page;

public interface IJobService {
  void createJob(String employerId, CreateJobRequest createJobRequest);

  Job getJobById(String jobId);

  void updateJob(String employerId, String jobId, CreateJobRequest createJobRequest);

  Page<GetEmployerJobsDto[]> getEmployerJobs(String employerId, int page, int size);
}
