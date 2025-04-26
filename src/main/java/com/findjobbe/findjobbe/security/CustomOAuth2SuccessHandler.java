package com.findjobbe.findjobbe.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.findjobbe.findjobbe.mapper.response.LoginResponse;
import com.findjobbe.findjobbe.service.IOAuth2Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

  private final IOAuth2Service oAuth2Service;

  @Value("${app.authorized-redirect-uri}")
  private String redirectUri;

  public CustomOAuth2SuccessHandler(IOAuth2Service oAuth2Service) {
    this.oAuth2Service = oAuth2Service;
  }

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException {
    OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
    String email = oAuth2User.getAttribute("email");
    LoginResponse loginResponse = oAuth2Service.login(email, oAuth2User);

    ObjectMapper objectMapper = new ObjectMapper();
    String accountDtoJson = objectMapper.writeValueAsString(loginResponse.getAccountDto());
    String encodedAccountDto = URLEncoder.encode(accountDtoJson, StandardCharsets.UTF_8);

    String targetUrl =
        UriComponentsBuilder.fromUriString(redirectUri)
            .queryParam("token", loginResponse.getToken())
            .queryParam("accountDto", encodedAccountDto)
            .queryParam("isNewAccount", loginResponse.getIsNewAccount())
            .build()
            .toUriString();

    response.sendRedirect(targetUrl);
  }
}
