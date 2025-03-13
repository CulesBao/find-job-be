package com.findjobbe.findjobbe.service.impl;

import com.findjobbe.findjobbe.exception.BadRequestException;
import com.findjobbe.findjobbe.exception.MessageConstants;
import com.findjobbe.findjobbe.model.Account;
import com.findjobbe.findjobbe.payload.request.RegisterRequest;
import com.findjobbe.findjobbe.payload.response.RegisterResponse;
import com.findjobbe.findjobbe.repository.AccountRepository;
import com.findjobbe.findjobbe.service.IAuthService;
import com.findjobbe.findjobbe.utils.GenerateCode;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements IAuthService {
  private final AccountRepository accountRepository;
  private final GenerateCode generateCode;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public AuthServiceImpl(
      AccountRepository accountRepository,
      GenerateCode generateCode,
      BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.accountRepository = accountRepository;
    this.generateCode = generateCode;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Override
  public RegisterResponse register(RegisterRequest registerRequest) {
    if (accountRepository.existsByEmail(registerRequest.getEmail())) {
      throw new BadRequestException(MessageConstants.EMAIL_ALREADY_EXISTS);
    }

    String code = generateCode.generateCode();
    String hashPassword = bCryptPasswordEncoder.encode(registerRequest.getPassword());
    Account account =
        Account.builder()
            .email(registerRequest.getEmail())
            .password(hashPassword)
            .isActive(false)
            .code(code)
            .expiredAt(LocalDateTime.now().plusMinutes(5))
            .provider(registerRequest.getProvider())
            .role(registerRequest.getRole())
            .province(null)
            .district(null)
            .socialLinks(null)
            .candidateProfile(null)
            .employerProfile(null)
            .build();
    Account newAccount = accountRepository.save(account);
    return new RegisterResponse("Register successfully", null);
  }
}
