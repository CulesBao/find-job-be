package com.findjobbe.findjobbe.service.impl;

import com.findjobbe.findjobbe.exception.MessageConstants;
import com.findjobbe.findjobbe.exception.UnauthorizedException;
import com.findjobbe.findjobbe.model.Account;
import com.findjobbe.findjobbe.model.CustomAccountDetails;
import com.findjobbe.findjobbe.repository.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private final AccountRepository accountRepository;

  public UserDetailsServiceImpl(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Account account =
        accountRepository
            .findByEmailAndIsActive(email, true)
            .orElseThrow(() -> new UnauthorizedException(MessageConstants.LOGIN_FAILED));
    return new CustomAccountDetails(account);
  }
}
