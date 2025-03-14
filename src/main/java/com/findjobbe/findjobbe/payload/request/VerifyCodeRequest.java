package com.findjobbe.findjobbe.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
public class VerifyCodeRequest {
  @NotNull
  @Length(min = 5, max = 5)
  private String code;
}
