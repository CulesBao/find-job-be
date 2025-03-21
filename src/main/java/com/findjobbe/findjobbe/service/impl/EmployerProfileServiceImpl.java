package com.findjobbe.findjobbe.service.impl;

import com.findjobbe.findjobbe.mapper.dto.BaseProfile;
import com.findjobbe.findjobbe.mapper.dto.SocialLinkDto;
import com.findjobbe.findjobbe.service.IProfileService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EmployerProfileServiceImpl implements IProfileService {

  @Override
  public BaseProfile createProfile(String id, BaseProfile profileRequest) {
    return null;
  }

  @Override
  public BaseProfile getProfile(String id) {
    return null;
  }

  @Override
  public void updateSocialLinks(String id, List<SocialLinkDto> socialLinks) {}

  @Override
  public void updateProfile(String accountId, BaseProfile profileRequest) {}

  @Override
  public void updateProfileImage(String accountId, MultipartFile image) {}
}
