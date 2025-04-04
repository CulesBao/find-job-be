package com.findjobbe.findjobbe.mapper.dto;

import com.findjobbe.findjobbe.model.Account;
import java.time.LocalDateTime;
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
  private UUID id;
  private String email;
  private boolean isActive;
  private String provider;
  private String role;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  @Builder
  public AccountDto(Account account) {
    this.id = account.getId();
    this.email = account.getEmail();
    this.isActive = account.isActive();
    this.provider = String.valueOf(account.getProvider());
    this.role = String.valueOf(account.getRole());
    this.createdAt = account.getCreatedAt();
    this.updatedAt = account.getUpdatedAt();
  }

  public boolean getIsActive() {
    return isActive;
  }
}
