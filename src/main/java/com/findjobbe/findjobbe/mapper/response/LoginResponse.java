package com.findjobbe.findjobbe.mapper.response;

import com.findjobbe.findjobbe.mapper.dto.AccountDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
  private String token;
  private AccountDto accountDto;
  private Boolean isNewAccount;
}
