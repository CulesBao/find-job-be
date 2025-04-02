package com.findjobbe.findjobbe.mapper.dto;

import com.findjobbe.findjobbe.enums.Currency;
import com.findjobbe.findjobbe.enums.Education;
import com.findjobbe.findjobbe.enums.JobType;
import com.findjobbe.findjobbe.enums.SalaryType;
import com.findjobbe.findjobbe.model.Job;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JobDto {
  private UUID id;
  private String title;
  private String description;
  private String responsibility;
  private JobType jobType;
  private double minSalary;
  private double maxSalary;
  private Currency currency;
  private SalaryType salaryType;
  private Education education;
  private String employerName;
  private String employerLogoUrl;
  private Date expiredAt;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public JobDto(Job job) {
    this.id = job.getId();
    this.title = job.getTitle();
    this.description = job.getDescription();
    this.responsibility = job.getResponsibility();
    this.jobType = job.getJobType();
    this.minSalary = job.getMinSalary();
    this.maxSalary = job.getMaxSalary();
    this.currency = job.getCurrency();
    this.salaryType = job.getSalaryType();
    this.education = job.getEducation();
    this.employerName = job.getEmployerProfile().getName();
    this.employerLogoUrl = job.getEmployerProfile().getLogoUrl();
    this.expiredAt = job.getExpiredAt();
    this.createdAt = job.getCreatedAt();
    this.updatedAt = job.getUpdatedAt();
  }
}
