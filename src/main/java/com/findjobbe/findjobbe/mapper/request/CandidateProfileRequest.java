package com.findjobbe.findjobbe.mapper.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.findjobbe.findjobbe.enums.Education;
import com.findjobbe.findjobbe.mapper.dto.BaseProfile;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CandidateProfileRequest extends BaseProfile {
  @NotNull
  @NotBlank
  @Length(min = 3, max = 50)
  private String firstName;

  @NotNull
  @NotBlank
  @Length(min = 3, max = 50)
  private String lastName;

  @Length(min = 6, max = 15)
  @NotNull
  private String phoneNumber;

  @NotNull private Date dateOfBirth;

  @NotNull private Boolean gender;

  @NotNull
  @Enumerated(EnumType.STRING)
  private Education education;

  @NotNull @NotBlank private String provinceCode;
  @NotNull @NotBlank private String districtCode;

  @NotNull private String bio;
}
