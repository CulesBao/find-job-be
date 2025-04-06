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
public class EmployerAppliedJobs {
  private UUID id;
  private String firstName;
  private String lastName;
  private String avatar_url;
  private String education;
  private Date createdAt;
  private String jobProccess;
}
