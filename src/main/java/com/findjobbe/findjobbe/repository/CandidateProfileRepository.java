package com.findjobbe.findjobbe.repository;

import com.findjobbe.findjobbe.model.CandidateProfile;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateProfileRepository extends JpaRepository<CandidateProfile, UUID> {
  Optional<CandidateProfile> findByAccountId(UUID accountId);
}
