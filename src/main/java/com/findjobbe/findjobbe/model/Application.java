package com.findjobbe.findjobbe.model;

import jakarta.persistence.*;

@Entity
@Table(name = "application")
public class Application extends AbstractEntity{
    @Column(name = "cv_url")
    private String cvUrl;

    @Column(name = "cover_letter", length = 2048)
    private String coverLetter;

    @Column(name = "is_favourite")
    private boolean isFavourite;

    @ManyToOne(targetEntity = CandidateProfile.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "candidate_profile_id", referencedColumnName = "id")
    private CandidateProfile candidateProfile;

    @ManyToOne(targetEntity = Job.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "job_id", referencedColumnName = "id")
    private Job job;
}
