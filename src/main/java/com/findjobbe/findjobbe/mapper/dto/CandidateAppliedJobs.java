package com.findjobbe.findjobbe.mapper.dto;

import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CandidateAppliedJobs {
  private UUID applicationId;
  private String logoUrl;
  private String name;
  private String location;
  private String title;
  private double minSalary;
  private double maxSalary;
  private String currency;
  private Date expiredAt;
  private String jobProccess;
  private UUID jobId;
}
