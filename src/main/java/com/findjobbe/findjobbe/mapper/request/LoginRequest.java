package com.findjobbe.findjobbe.mapper.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginRequest {
  @NotNull private String email;

  @NotNull private String password;
}
