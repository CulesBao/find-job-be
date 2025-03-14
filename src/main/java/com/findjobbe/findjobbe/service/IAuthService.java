package com.findjobbe.findjobbe.service;

import com.findjobbe.findjobbe.payload.request.RegisterRequest;
import com.findjobbe.findjobbe.payload.request.VerifyCodeRequest;
import com.findjobbe.findjobbe.payload.response.AbstractResponse;
import jakarta.mail.MessagingException;

public interface IAuthService {
  AbstractResponse register(RegisterRequest registerRequest) throws MessagingException;

  AbstractResponse verifyCode(String accountId, VerifyCodeRequest verifyCodeRequest);
}
