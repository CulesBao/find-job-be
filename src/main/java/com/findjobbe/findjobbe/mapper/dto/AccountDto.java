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
  private UUID id;
  private String email;
  private boolean isActive;
  private String provider;
  private String role;
  private CandidateProfileDto candidateProfile;
  private EmployerProfileDto employerProfile;
  private String createdAt;
  private String updatedAt;

  public AccountDto(Account account) {
    this.id = account.getId();
    this.email = account.getEmail();
    this.isActive = account.isActive();
    this.provider = String.valueOf(account.getProvider());
    this.role = String.valueOf(account.getRole());
    this.candidateProfile =
        (account.getCandidateProfile() != null)
            ? new CandidateProfileDto(account.getCandidateProfile())
            : null;
    this.employerProfile =
        (account.getEmployerProfile() != null)
            ? new EmployerProfileDto(account.getEmployerProfile())
            : null;
    this.createdAt = account.getCreatedAt() != null ? account.getCreatedAt().toString() : null;
    this.updatedAt = account.getUpdatedAt() != null ? account.getUpdatedAt().toString() : null;
  }

  public boolean getIsActive() {
    return isActive;
  }
}
