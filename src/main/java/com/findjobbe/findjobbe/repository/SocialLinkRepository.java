package com.findjobbe.findjobbe.repository;

import com.findjobbe.findjobbe.model.CandidateProfile;
import com.findjobbe.findjobbe.model.SocialLink;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialLinkRepository extends JpaRepository<SocialLink, UUID> {
  void deleteByCandidateProfile(CandidateProfile candidateProfile);
}
