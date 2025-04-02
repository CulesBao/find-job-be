package com.findjobbe.findjobbe.mapper.request;

import com.findjobbe.findjobbe.enums.Currency;
import com.findjobbe.findjobbe.enums.Education;
import com.findjobbe.findjobbe.enums.JobType;
import com.findjobbe.findjobbe.enums.SalaryType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
public class CreateJobRequest {
  @NotNull
  @NotBlank
  @Length(min = 6, max = 100)
  private String title;

  @NotNull
  @NotBlank
  @Length(min = 10, max = 2048)
  private String description;

  @NotNull private String responsibility;

  @Enumerated(EnumType.STRING)
  private JobType jobType;

  @NotNull @Positive private double minSalary;

  @NotNull @Positive private double maxSalary;

  @NotNull
  @Enumerated(EnumType.STRING)
  private Currency currency;

  @Enumerated(EnumType.STRING)
  private SalaryType salaryType;

  @Enumerated(EnumType.STRING)
  private Education education;

  @NotNull private Date expiredAt;
}
