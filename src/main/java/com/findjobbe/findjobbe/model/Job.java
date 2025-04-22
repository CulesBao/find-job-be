package com.findjobbe.findjobbe.model;

import com.findjobbe.findjobbe.enums.Currency;
import com.findjobbe.findjobbe.enums.Education;
import com.findjobbe.findjobbe.enums.JobType;
import com.findjobbe.findjobbe.enums.SalaryType;
import com.findjobbe.findjobbe.mapper.request.CreateJobRequest;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "job")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Job extends AbstractEntity {
  @Column(nullable = false)
  private String title;

  @Column(length = 2048)
  private String description;

  @Column(length = 2048)
  private String responsibility;

  @Column(name = "job_type")
  @Enumerated(EnumType.STRING)
  private JobType jobType;

  @Column(name = "min_salary")
  private double minSalary;

  @Column(name = "max_salary")
  private double maxSalary;

  @Enumerated(EnumType.STRING)
  @Column(name = "salary_type")
  private SalaryType salaryType;

  @Column
  @Enumerated(EnumType.STRING)
  private Education education;

  @Column(name = "expired_at")
  private Date expiredAt;

  @Column(name = "currency")
  @Enumerated(EnumType.STRING)
  private Currency currency;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "employer_id", referencedColumnName = "id")
  private EmployerProfile employerProfile;

  @OneToMany(mappedBy = "job", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Application> applications;

  public Job(CreateJobRequest createJobRequest, EmployerProfile employerProfile) {
    this.title = createJobRequest.getTitle();
    this.description = createJobRequest.getDescription();
    this.responsibility = createJobRequest.getResponsibility();
    this.jobType = createJobRequest.getJobType();
    this.education = createJobRequest.getEducation();
    this.minSalary = createJobRequest.getMinSalary();
    this.maxSalary = createJobRequest.getMaxSalary();
    this.salaryType = createJobRequest.getSalaryType();
    this.currency = createJobRequest.getCurrency();
    this.expiredAt = createJobRequest.getExpiredAt();
    this.employerProfile = employerProfile;
  }

  public void update(CreateJobRequest createJobRequest) {
    this.title = createJobRequest.getTitle();
    this.description = createJobRequest.getDescription();
    this.responsibility = createJobRequest.getResponsibility();
    this.jobType = createJobRequest.getJobType();
    this.education = createJobRequest.getEducation();
    this.minSalary = createJobRequest.getMinSalary();
    this.maxSalary = createJobRequest.getMaxSalary();
    this.salaryType = createJobRequest.getSalaryType();
    this.currency = createJobRequest.getCurrency();
    this.expiredAt = createJobRequest.getExpiredAt();
  }
}
