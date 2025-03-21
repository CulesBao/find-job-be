package com.findjobbe.findjobbe.mapper.request;

import com.findjobbe.findjobbe.mapper.dto.BaseProfile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployerProfileRequest extends BaseProfile {
  @NotNull
  @NotBlank
  @Length(min = 6, max = 100)
  private String name;

  @NotNull
  @NotBlank
  @Length(min = 50, max = 500)
  private String about;

  @NotNull
  @NotBlank
  @Length(min = 50, max = 500)
  private String vision;

  @NotNull private String websiteUrl;
  @NotNull private Date establishedIn;
  @NotNull private String location;
}
