package com.findjobbe.findjobbe.mapper.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.findjobbe.findjobbe.enums.Provider;
import com.findjobbe.findjobbe.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RegisterRequest {
  @NotNull
  @Length(min = 6, max = 50)
  @Email
  private String email;

  @NotNull
  @Length(min = 8, max = 100)
  private String password;

  @NotNull private Role role;

  @NotNull private Provider provider;
}
