package com.findjobbe.findjobbe.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "employer_profile")
public class EmployerProfile extends AbstractEntity{
    @Column(nullable = false)
    private String name;

    @Column
    private String about;

    @Column
    private String vision;

    @Column
    private String website_url;

    @Column
    private String logo_url;

    @Column(name = "established_in")
    private LocalDateTime establishedIn;

    @OneToOne(mappedBy = "employerProfile", cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "employerProfile", cascade = CascadeType.ALL)
    private List<Job> jobs;
}
