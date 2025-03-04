package com.findjobbe.findjobbe.model;

import com.findjobbe.findjobbe.enums.Education;
import com.findjobbe.findjobbe.enums.JobType;
import com.findjobbe.findjobbe.enums.SalaryType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "job")
public class Job extends AbstractEntity {
    @Column(nullable = false)
    private String title;

    @Column(length = 2048)
    private String description;
    
    @Column(length = 2048)
    private String responsibility;

    @Column
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
    private LocalDateTime expiredAt;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "employer_id", referencedColumnName = "id")
    private EmployerProfile employerProfile;

    @OneToMany(mappedBy = "job", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Application> applications;
}
