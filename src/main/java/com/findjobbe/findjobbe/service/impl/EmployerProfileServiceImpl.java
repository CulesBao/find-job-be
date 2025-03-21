package com.findjobbe.findjobbe.service.impl;

import com.findjobbe.findjobbe.mapper.dto.BaseProfile;
import com.findjobbe.findjobbe.service.IProfileService;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class EmployerProfileServiceImpl implements IProfileService {

  @Override
  public BaseProfile createProfile(UUID id, BaseProfile profileRequest) {
    return null;
  }
}
