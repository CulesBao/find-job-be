package com.findjobbe.findjobbe.mapper.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
public class ResetPasswordRequest {
  @NotNull
  @Length(min = 6)
  private String oldPassword;
  @NotNull
  @Length(min = 8, max = 100)
  private String newPassword;

  @NotNull
  @Length(min = 8, max = 100)
  private String confirmPassword;
}
