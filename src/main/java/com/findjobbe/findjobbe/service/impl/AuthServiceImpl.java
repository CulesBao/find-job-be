package com.findjobbe.findjobbe.service.impl;

import com.findjobbe.findjobbe.exception.*;
import com.findjobbe.findjobbe.mapper.dto.AccountDto;
import com.findjobbe.findjobbe.mapper.request.LoginRequest;
import com.findjobbe.findjobbe.mapper.request.RegisterRequest;
import com.findjobbe.findjobbe.mapper.request.VerifyCodeRequest;
import com.findjobbe.findjobbe.model.Account;
import com.findjobbe.findjobbe.repository.AccountRepository;
import com.findjobbe.findjobbe.security.JwtTokenManager;
import com.findjobbe.findjobbe.service.IAuthService;
import com.findjobbe.findjobbe.service.IMailService;
import com.findjobbe.findjobbe.utils.GenerateCode;
import jakarta.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements IAuthService {
  private final AccountRepository accountRepository;
  private final GenerateCode generateCode;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final IMailService mailService;
  private final AuthenticationManager authenticationManager;
  private final JwtTokenManager jwtTokenManager;

  @Autowired
  public AuthServiceImpl(
      AccountRepository accountRepository,
      GenerateCode generateCode,
      BCryptPasswordEncoder bCryptPasswordEncoder,
      IMailService mailService,
      JwtTokenManager jwtTokenManager,
      AuthenticationManager authenticationManager) {
    this.accountRepository = accountRepository;
    this.generateCode = generateCode;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.mailService = mailService;
    this.jwtTokenManager = jwtTokenManager;
    this.authenticationManager = authenticationManager;
  }

  @Override
  public AccountDto register(RegisterRequest registerRequest) throws MessagingException {
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
    AccountDto accountDto =
        AccountDto.builder()
            .id(savedAccount.getId())
            .email(savedAccount.getEmail())
            .isActive(savedAccount.getIsActive())
            .provider(String.valueOf(savedAccount.getProvider()))
            .role(String.valueOf(savedAccount.getRole()))
            .build();
    mailService.sendVerifyCode(savedAccount.getEmail(), savedAccount.getCode());
    return accountDto;
  }

  @Override
  public void verifyCode(String accountId, VerifyCodeRequest verifyCodeRequest) {
    Account account =
        accountRepository
            .findByCode(UUID.fromString(accountId), verifyCodeRequest.getCode())
            .orElseThrow(() -> new BadRequestException(MessageConstants.INVALID_CODE));
    account.setIsActive(true);
    accountRepository.save(account);
  }

  @Override
  public String login(LoginRequest loginRequest) {
    Account account =
        accountRepository
            .findByEmailAndIsActive(loginRequest.getEmail(), true)
            .orElseThrow(() -> new UnauthorizedException(MessageConstants.LOGIN_FAILED));

    try {
      UsernamePasswordAuthenticationToken authenticationToken =
          new UsernamePasswordAuthenticationToken(
              loginRequest.getEmail(), loginRequest.getPassword());

      authenticationManager.authenticate(authenticationToken);
    } catch (BadCredentialsException e) {
      throw new UnauthorizedException(MessageConstants.LOGIN_FAILED);
    }

    return jwtTokenManager.generateToken(account);
  }
}
