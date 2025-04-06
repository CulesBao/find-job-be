package com.findjobbe.findjobbe.factory;

import com.findjobbe.findjobbe.enums.Role;
import com.findjobbe.findjobbe.exception.MessageConstants;
import com.findjobbe.findjobbe.exception.NotFoundException;
import com.findjobbe.findjobbe.service.IFollowerService;
import com.findjobbe.findjobbe.service.impl.CandidateFollowerServiceImpl;
import com.findjobbe.findjobbe.service.impl.EmployerFollowerServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class FollowerServiceFactory {
  private final CandidateFollowerServiceImpl candidateFollowerService;
  private final EmployerFollowerServiceImpl employerFollowerService;

  public FollowerServiceFactory(
      CandidateFollowerServiceImpl candidateFollowerService,
      EmployerFollowerServiceImpl employerFollowerService) {
    this.candidateFollowerService = candidateFollowerService;
    this.employerFollowerService = employerFollowerService;
  }

  public IFollowerService getFollowerService(Role profileType) {
    return switch (profileType) {
      case CANDIDATE -> candidateFollowerService;
      case EMPLOYER -> employerFollowerService;
      default -> throw new NotFoundException(MessageConstants.PROFILE_NOT_FOUND);
    };
  }
}
