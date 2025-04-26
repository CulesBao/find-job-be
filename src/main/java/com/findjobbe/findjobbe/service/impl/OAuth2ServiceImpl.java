package com.findjobbe.findjobbe.service.impl;

import com.findjobbe.findjobbe.enums.Provider;
import com.findjobbe.findjobbe.enums.Role;
import com.findjobbe.findjobbe.exception.MessageConstants;
import com.findjobbe.findjobbe.exception.NotFoundException;
import com.findjobbe.findjobbe.mapper.dto.AccountDto;
import com.findjobbe.findjobbe.mapper.response.LoginResponse;
import com.findjobbe.findjobbe.model.Account;
import com.findjobbe.findjobbe.repository.AccountRepository;
import com.findjobbe.findjobbe.security.JwtTokenManager;
import com.findjobbe.findjobbe.service.IOAuth2Service;
import com.findjobbe.findjobbe.utils.GenerateCode;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class OAuth2ServiceImpl implements IOAuth2Service {
  private final AccountRepository accountRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final GenerateCode generateCode;
  private final JwtTokenManager jwtTokenManager;

  @Value("${default-google-password}")
  private String defaultGooglePassword;

  public OAuth2ServiceImpl(
      AccountRepository accountRepository,
      BCryptPasswordEncoder bCryptPasswordEncoder,
      GenerateCode generateCode,
      JwtTokenManager jwtTokenManager) {
    this.accountRepository = accountRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.generateCode = generateCode;
    this.jwtTokenManager = jwtTokenManager;
  }

  Role getRoleByEmail(String email) {
    List<String> personalDomains =
        List.of("gmail.com", "yahoo.com", "outlook.com", "hotmail.com", "icloud.com");
    String domain = email.substring(email.indexOf("@") + 1);
    if (personalDomains.contains(domain)) {
      return Role.CANDIDATE;
    } else {
      return Role.EMPLOYER;
    }
  }

  @Override
  @Transactional
  public LoginResponse login(String email, OAuth2User oAuth2User) {
    String action = isExits(email);
    if (action.equals("NOT_EXISTS")) {
      Account account = register(email, oAuth2User);
      return new LoginResponse(
          jwtTokenManager.generateToken(account),
          new AccountDto(account),
          account.getEmployerProfile() == null && account.getCandidateProfile() == null);
    }
    if (action.equals("CAN_LOGIN")) {
      Account account =
          accountRepository
              .findByEmailAndIsActiveAndProvider(email, true, Provider.GOOGLE)
              .orElseThrow(() -> new NotFoundException(MessageConstants.ACCOUNT_NOT_FOUND));
      return new LoginResponse(
          jwtTokenManager.generateToken(account),
          new AccountDto(account),
          account.getEmployerProfile() == null && account.getCandidateProfile() == null);
    }
    return new LoginResponse(null, null, false);
  }

  private Account register(String email, OAuth2User oAuth2User) {
    Account account =
        Account.builder()
            .email(email)
            .password(bCryptPasswordEncoder.encode(defaultGooglePassword))
            .isActive(true)
            .code(generateCode.generateCode())
            .expiredAt(LocalDateTime.now().plusMinutes(5))
            .provider(Provider.GOOGLE)
            .role(getRoleByEmail(email))
            .candidateProfile(null)
            .employerProfile(null)
            .build();

    return accountRepository.save(account);
  }

  private String isExits(String email) {
    if (!accountRepository.existsByEmail(email)) return "NOT_EXISTS";
    if (accountRepository.existsByEmailAndProvider(email, Provider.GOOGLE)) return "CAN_LOGIN";
    return "CAN_NOT_LOGIN";
  }
}
