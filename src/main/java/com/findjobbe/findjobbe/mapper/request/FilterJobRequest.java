package com.findjobbe.findjobbe.mapper.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class FilterJobRequest {
  private String title;
  private String provinceCode;
  private String jobType;
  private double minSalary;
  private double maxSalary;
  private String currency;
  private String salaryType;
  private String education;
}
