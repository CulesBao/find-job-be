package com.findjobbe.findjobbe.service;

import com.findjobbe.findjobbe.mapper.dto.AccountDto;
import com.findjobbe.findjobbe.mapper.request.LoginRequest;
import com.findjobbe.findjobbe.mapper.request.RegisterRequest;
import com.findjobbe.findjobbe.mapper.request.ResetPasswordRequest;
import com.findjobbe.findjobbe.mapper.request.VerifyCodeRequest;
import com.findjobbe.findjobbe.mapper.response.LoginResponse;
import com.findjobbe.findjobbe.model.Account;
import jakarta.mail.MessagingException;

public interface IAccountService {
  AccountDto register(RegisterRequest registerRequest) throws MessagingException;

  void verifyCode(String accountId, VerifyCodeRequest verifyCodeRequest);

  LoginResponse login(LoginRequest loginRequest);

  Account getAccountById(String accountId);

  void resetPassword(String accountId, ResetPasswordRequest resetPasswordRequest);
}
