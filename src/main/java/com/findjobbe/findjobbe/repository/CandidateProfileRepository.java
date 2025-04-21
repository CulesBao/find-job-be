package com.findjobbe.findjobbe.repository;

import com.findjobbe.findjobbe.mapper.dto.FilterCandidateDto;
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

  @Query(
      value =
          "SELECT c.id, c.first_name AS firstName, c.last_name AS lastName, c.avatar_url AS avatarUrl, "
              + "p.name_en AS location, "
              + "c.education, "
              + "c.gender "
              + "FROM candidate_profile c "
              + "JOIN provinces p ON c.province_id = p.code "
              + "WHERE (COALESCE(:firstName, '') = '' OR c.first_name LIKE CONCAT('%', :firstName, '%')) "
              + "AND (COALESCE(:lastName, '') = '' OR c.last_name LIKE CONCAT('%', :lastName, '%')) "
              + "AND (COALESCE(:education, '') = '' OR c.education = :education) "
              + "AND (COALESCE(:provinceCode, '') = '' OR c.province_id = :provinceCode) "
              + "AND (:gender IS NULL OR (c.gender = :gender))",
      countQuery =
          "SELECT COUNT(*) "
              + "FROM candidate_profile c "
              + "JOIN provinces p ON c.province_id = p.code "
              + "WHERE (COALESCE(:firstName, '') = '' OR c.first_name ILIKE CONCAT('%', :firstName, '%')) "
              + "AND (COALESCE(:lastName, '') = '' OR c.last_name ILIKE CONCAT('%', :lastName, '%')) "
              + "AND (COALESCE(:education, '') = '' OR c.education = :education) "
              + "AND (COALESCE(:provinceCode, '') = '' OR c.province_id = :provinceCode) "
              + "AND (:gender IS NULL OR (c.gender = :gender))",
      nativeQuery = true)
  Page<FilterCandidateDto[]> filterCandidate(
      @Param("firstName") String firstName,
      @Param("lastName") String lastName,
      @Param("education") String education,
      @Param("provinceCode") String provinceCode,
      @Param("gender") Boolean gender,
      Pageable pageable);
}
