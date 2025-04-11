package com.findjobbe.findjobbe.mapper.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
  private String token;
  private Boolean isNewAccount;
}
