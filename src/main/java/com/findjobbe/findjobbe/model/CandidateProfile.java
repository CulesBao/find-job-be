package com.findjobbe.findjobbe.model;

import com.findjobbe.findjobbe.enums.Education;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "candidate_profile")
public class CandidateProfile extends AbstractEntity {
    @Column(nullable = false, name = "first_name")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;

    @Column
    private Boolean gender;

    @Column
    @Enumerated(EnumType.STRING)
    private Education education;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column
    private String bio;

    @OneToOne(mappedBy = "candidateProfile", cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "candidateProfile", cascade = CascadeType.ALL)
    private List<Application> applications;
}
