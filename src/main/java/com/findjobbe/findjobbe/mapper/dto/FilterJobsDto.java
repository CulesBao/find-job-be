package com.findjobbe.findjobbe.mapper.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilterJobsDto {
  private String logoUrl;
  private String name;
  private String location;
  private String title;
  private double minSalary;
  private double maxSalary;
  private String currency;
  private Date expiredAt;
}
