package com.findjobbe.findjobbe.mapper.dto;

import com.findjobbe.findjobbe.model.Account;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class AccountDto {
  @Builder
  public AccountDto(Account account) {
    this.id = account.getId();
    this.email = account.getEmail();
    this.isActive = account.isActive();
    this.provider = String.valueOf(account.getProvider());
    this.role = String.valueOf(account.getRole());
  }

  private UUID id;
  private String email;
  private boolean isActive;
  private String provider;
  private String role;
}
