package com.findjobbe.findjobbe.repository;

import com.findjobbe.findjobbe.mapper.dto.SaveCandidateDto;
import com.findjobbe.findjobbe.model.CandidateProfile;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CandidateProfileRepository extends JpaRepository<CandidateProfile, UUID> {
  Optional<CandidateProfile> findByAccountId(UUID accountId);

  @Query(
      value =
          "SELECT c.id, c.first_name AS firstName, c.last_name as lastName, p.name_en AS location, c.avatar_url AS avatarUrl FROM candidate_profile c"
              + " JOIN saved_candidates sc ON c.id = sc.candidate_id"
              + " JOIN provinces p ON c.province_id = p.code"
              + " WHERE sc.employer_id = :employerId",
      countQuery =
          "SELECT COUNT(*) FROM candidate_profile c"
              + " JOIN saved_candidates sc ON c.id = sc.candidate_id"
              + " JOIN provinces p ON c.province_id = p.code"
              + " WHERE sc.employer_id = :employerId",
      nativeQuery = true)
  Page<SaveCandidateDto[]> findAllByEmployerId(
      @Param("employerId") UUID employerId, Pageable pageable);
}
