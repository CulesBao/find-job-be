package com.findjobbe.findjobbe.service;

import com.findjobbe.findjobbe.mapper.dto.BaseProfile;
import java.util.UUID;

public interface IProfileService {
  BaseProfile createProfile(UUID id, BaseProfile profileRequest);
}
