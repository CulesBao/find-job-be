package com.findjobbe.findjobbe.service;

import com.findjobbe.findjobbe.mapper.dto.AccountDto;
import com.findjobbe.findjobbe.mapper.request.LoginRequest;
import com.findjobbe.findjobbe.mapper.request.RegisterRequest;
import com.findjobbe.findjobbe.mapper.request.VerifyCodeRequest;
import com.findjobbe.findjobbe.model.Account;
import jakarta.mail.MessagingException;

public interface IAccountService {
  AccountDto register(RegisterRequest registerRequest) throws MessagingException;

  void verifyCode(String accountId, VerifyCodeRequest verifyCodeRequest);

  String login(LoginRequest loginRequest);

  Account getAccountById(String accountId);

  void resetPassword(String accountId, String newPassword, String confirmPassword);
}
