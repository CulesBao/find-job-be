package com.findjobbe.findjobbe.factory;

import com.findjobbe.findjobbe.enums.Role;
import com.findjobbe.findjobbe.exception.MessageConstants;
import com.findjobbe.findjobbe.exception.NotFoundException;
import com.findjobbe.findjobbe.service.IProfileService;
import com.findjobbe.findjobbe.service.impl.CandidateProfileServiceImpl;
import com.findjobbe.findjobbe.service.impl.EmployerProfileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProfileServiceFactory {
  private final CandidateProfileServiceImpl candidateProfile;
  private final EmployerProfileServiceImpl employerProfile;

  @Autowired
  public ProfileServiceFactory(
      CandidateProfileServiceImpl candidateProfile,
      EmployerProfileServiceImpl employerProfile) {
    this.candidateProfile = candidateProfile;
    this.employerProfile = employerProfile;
  }

  public IProfileService getProfileService(Role profileType) {
    return switch (profileType) {
      case CANDIDATE -> candidateProfile;
      case EMPLOYER -> employerProfile;
      default -> throw new NotFoundException(MessageConstants.PROFILE_NOT_FOUND);
    };
  }
}
