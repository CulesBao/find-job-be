package com.findjobbe.findjobbe.service;

import com.findjobbe.findjobbe.mapper.dto.BaseProfile;
import com.findjobbe.findjobbe.mapper.dto.SocialLinkDto;
import java.util.List;

public interface IProfileService {
  BaseProfile createProfile(String accountId, BaseProfile profileRequest);

  BaseProfile getProfile(String profileId);

  void updateSocialLinks(String accountId, List<SocialLinkDto> socialLinks);

  void updateProfile(String accountId, BaseProfile profileRequest);
}
