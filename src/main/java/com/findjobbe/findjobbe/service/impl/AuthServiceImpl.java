package com.findjobbe.findjobbe.service.impl;

import com.findjobbe.findjobbe.exception.BadRequestException;
import com.findjobbe.findjobbe.exception.MessageConstants;
import com.findjobbe.findjobbe.model.Account;
import com.findjobbe.findjobbe.payload.dto.AccountDto;
import com.findjobbe.findjobbe.payload.request.RegisterRequest;
import com.findjobbe.findjobbe.payload.request.VerifyCodeRequest;
import com.findjobbe.findjobbe.payload.response.AbstractResponse;
import com.findjobbe.findjobbe.repository.AccountRepository;
import com.findjobbe.findjobbe.service.IAuthService;
import com.findjobbe.findjobbe.service.IMailService;
import com.findjobbe.findjobbe.utils.GenerateCode;
import jakarta.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements IAuthService {
  private final AccountRepository accountRepository;
  private final GenerateCode generateCode;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final IMailService mailService;

  @Autowired
  public AuthServiceImpl(
      AccountRepository accountRepository,
      GenerateCode generateCode,
      BCryptPasswordEncoder bCryptPasswordEncoder,
      IMailService mailService) {
    this.accountRepository = accountRepository;
    this.generateCode = generateCode;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.mailService = mailService;
  }

  @Override
  public AbstractResponse register(RegisterRequest registerRequest) throws MessagingException {
    if (accountRepository.existsByEmail(registerRequest.getEmail())) {
      throw new BadRequestException(MessageConstants.EMAIL_ALREADY_EXISTS);
    }

    Account account =
        Account.builder()
            .email(registerRequest.getEmail())
            .password(bCryptPasswordEncoder.encode(registerRequest.getPassword()))
            .isActive(false)
            .code(generateCode.generateCode())
            .expiredAt(LocalDateTime.now().plusMinutes(5))
            .provider(registerRequest.getProvider())
            .role(registerRequest.getRole())
            .province(null)
            .district(null)
            .socialLinks(null)
            .candidateProfile(null)
            .employerProfile(null)
            .build();
    Account savedAccount = accountRepository.save(account);
    AccountDto newAccount =
        AccountDto.builder()
            .id(savedAccount.getId())
            .email(savedAccount.getEmail())
            .isActive(savedAccount.getIsActive())
            .provider(String.valueOf(savedAccount.getProvider()))
            .role(String.valueOf(savedAccount.getRole()))
            .build();
    mailService.sendVerifyCode(savedAccount.getEmail(), savedAccount.getCode());
    return new AbstractResponse("Register successfully", Optional.of(newAccount));
  }

  @Override
  public AbstractResponse verifyCode(String accountId, VerifyCodeRequest verifyCodeRequest) {
    Account account =
        accountRepository
            .findByCode(UUID.fromString(accountId), verifyCodeRequest.getCode())
            .orElseThrow(() -> new BadRequestException(MessageConstants.INVALID_CODE));
    account.setIsActive(true);
    accountRepository.save(account);
    return new AbstractResponse("Verify code successfully", null);
  }
}
