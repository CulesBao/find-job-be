package com.findjobbe.findjobbe.service;

import com.findjobbe.findjobbe.mapper.response.LoginResponse;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface IOAuth2Service {
  LoginResponse login(String email, OAuth2User oAuth2User);
}
