package com.findjobbe.findjobbe.mapper.dto;

import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetEmployerJobsDto {
  private UUID id;
  private String title;
  private String jobType;
  private Date expiredAt;
  private long appliedCount;
}
