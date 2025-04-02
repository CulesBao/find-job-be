package com.findjobbe.findjobbe.service;

import com.findjobbe.findjobbe.exception.ForbiddenException;
import com.findjobbe.findjobbe.mapper.dto.ApplicationDto;
import com.findjobbe.findjobbe.mapper.dto.GetCandidateAppliedJobs;
import com.findjobbe.findjobbe.mapper.dto.GetEmployerAppliedJobs;
import com.findjobbe.findjobbe.model.CustomAccountDetails;
import java.io.IOException;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface IApplicationService {
  void applyForJob(String jobId, String candidateId, String coverLetter, MultipartFile file)
      throws ForbiddenException, IOException;

  void withdrawApplication(String candidateId, String applicationId);

  Page<GetCandidateAppliedJobs[]> getCandidateAppliedJobs(String candidateId, int page, int size);

  Page<GetEmployerAppliedJobs[]> getEmployerAppliedJobs(
      String employerId, String jobId, int page, int size);

  ApplicationDto getApplicationById(
      String applicationId, CustomAccountDetails customAccountDetails);
}
