package com.findjobbe.findjobbe.model;

import com.findjobbe.findjobbe.enums.JobProccess;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "application")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Application extends AbstractEntity {
  @Column(name = "cv_url")
  private String cvUrl;

  @Column(name = "cover_letter", length = 2048)
  private String coverLetter;

  @ManyToOne(targetEntity = CandidateProfile.class, cascade = CascadeType.ALL)
  @JoinColumn(name = "candidate_profile_id", referencedColumnName = "id")
  private CandidateProfile candidateProfile;

  @ManyToOne(targetEntity = Job.class, cascade = CascadeType.ALL)
  @JoinColumn(name = "job_id", referencedColumnName = "id")
  private Job job;

  @Column
  @Enumerated(EnumType.STRING)
  private JobProccess proccess;
}
