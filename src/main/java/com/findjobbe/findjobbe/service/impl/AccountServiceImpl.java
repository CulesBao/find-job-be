package com.findjobbe.findjobbe.service.impl;

import com.findjobbe.findjobbe.enums.Provider;
import com.findjobbe.findjobbe.exception.*;
import com.findjobbe.findjobbe.mapper.dto.AccountDto;
import com.findjobbe.findjobbe.mapper.request.LoginRequest;
import com.findjobbe.findjobbe.mapper.request.RegisterRequest;
import com.findjobbe.findjobbe.mapper.request.ResetPasswordRequest;
import com.findjobbe.findjobbe.mapper.request.VerifyCodeRequest;
import com.findjobbe.findjobbe.mapper.response.LoginResponse;
import com.findjobbe.findjobbe.model.Account;
import com.findjobbe.findjobbe.repository.AccountRepository;
import com.findjobbe.findjobbe.security.JwtTokenManager;
import com.findjobbe.findjobbe.service.IAccountService;
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
public class AccountServiceImpl implements IAccountService {
  private final AccountRepository accountRepository;
  private final GenerateCode generateCode;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final IMailService mailService;
  private final AuthenticationManager authenticationManager;
  private final JwtTokenManager jwtTokenManager;

  @Autowired
  public AccountServiceImpl(
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
            .candidateProfile(null)
            .employerProfile(null)
            .build();
    Account savedAccount = accountRepository.save(account);
    AccountDto accountDto = new AccountDto(savedAccount);
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
  public LoginResponse login(LoginRequest loginRequest) {
    Account account =
        accountRepository
            .findByEmailAndIsActiveAndProvider(loginRequest.getEmail(), true, Provider.LOCAL)
            .orElseThrow(() -> new UnauthorizedException(MessageConstants.LOGIN_FAILED));

    try {
      UsernamePasswordAuthenticationToken authenticationToken =
          new UsernamePasswordAuthenticationToken(
              loginRequest.getEmail(), loginRequest.getPassword());

      authenticationManager.authenticate(authenticationToken);
    } catch (BadCredentialsException e) {
      throw new UnauthorizedException(MessageConstants.LOGIN_FAILED);
    }

    return new LoginResponse(
        jwtTokenManager.generateToken(account),
        new AccountDto(account),
        account.getEmployerProfile() == null && account.getCandidateProfile() == null);
  }

  @Override
  public Account getAccountById(String accountId) {
    return accountRepository
        .findById(UUID.fromString(accountId))
        .orElseThrow(() -> new NotFoundException(MessageConstants.ACCOUNT_NOT_FOUND));
  }

  @Override
  public void resetPassword(String accountId, ResetPasswordRequest resetPasswordRequest) {
    if (!resetPasswordRequest.getNewPassword().equals(resetPasswordRequest.getConfirmPassword())) {
      throw new BadRequestException(MessageConstants.PASSWORD_NOT_MATCH);
    }
    Account account = getAccountById(accountId);
    if (!bCryptPasswordEncoder.matches(
        resetPasswordRequest.getOldPassword(), account.getPassword())) {
      throw new BadRequestException(MessageConstants.OLD_PASSWORD_NOT_MATCH);
    }
    if (bCryptPasswordEncoder.matches(
        resetPasswordRequest.getNewPassword(), account.getPassword())) {
      throw new ForbiddenException(MessageConstants.PASSWORD_MUST_BE_DIFFERENT);
    }

    account.setPassword(bCryptPasswordEncoder.encode(resetPasswordRequest.getNewPassword()));
    accountRepository.save(account);
  }
}
