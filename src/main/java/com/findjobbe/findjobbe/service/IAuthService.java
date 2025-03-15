package com.findjobbe.findjobbe.service;

import com.findjobbe.findjobbe.mapper.dto.AccountDto;
import com.findjobbe.findjobbe.mapper.request.LoginRequest;
import com.findjobbe.findjobbe.mapper.request.RegisterRequest;
import com.findjobbe.findjobbe.mapper.request.VerifyCodeRequest;
import jakarta.mail.MessagingException;

public interface IAuthService {
  AccountDto register(RegisterRequest registerRequest) throws MessagingException;

  void verifyCode(String accountId, VerifyCodeRequest verifyCodeRequest);

  String login(LoginRequest loginRequest);
}
