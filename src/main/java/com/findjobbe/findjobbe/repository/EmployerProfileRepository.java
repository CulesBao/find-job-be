package com.findjobbe.findjobbe.repository;

import com.findjobbe.findjobbe.mapper.dto.FilterEmployerDto;
import com.findjobbe.findjobbe.mapper.dto.SaveEmployerDto;
import com.findjobbe.findjobbe.model.EmployerProfile;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployerProfileRepository extends JpaRepository<EmployerProfile, UUID> {
  Optional<EmployerProfile> findByAccountId(UUID uuid);

  boolean existsByAccountId(UUID uuid);

  @Query(
      value =
          "SELECT e.id, e.name, p.name_en AS location, e.logo_url AS logoUrl, "
              + "CAST((SELECT COUNT(*) FROM job j WHERE j.employer_id = e.id AND j.expired_at >= CURRENT_DATE) AS INTEGER) AS jobCount "
              + "FROM employer_profile e "
              + "JOIN provinces p ON e.province_id = p.code "
              + "JOIN saved_employers se ON e.id = se.employer_id "
              + "WHERE se.candidate_id = :candidateId",
      countQuery =
          "SELECT COUNT(*) FROM employer_profile e "
              + "JOIN provinces p ON e.province_id = p.code "
              + "JOIN saved_employers se ON e.id = se.employer_id "
              + "WHERE se.candidate_id = :candidateId",
      nativeQuery = true)
  Page<SaveEmployerDto[]> findAllByCandidateId(
      @Param("candidateId") UUID candidateId, Pageable pageable);

  @Query(
      value =
          "SELECT e.id, e.name, e.logo_url AS logoUrl, p.name_en AS location, "
                  + "CAST((SELECT COUNT(*) FROM job j WHERE j.employer_id = e.id AND j.expired_at >= CURRENT_DATE) AS INTEGER) AS jobCount "
                  + "FROM employer_profile e "
                  + "JOIN provinces p ON e.province_id = p.code "
                  + "WHERE (COALESCE(:name, '') = '' OR e.name LIKE CONCAT('%', :name, '%')) "
                  + "AND (COALESCE(:provinceCode, '') = '' OR p.code = :provinceCode)",
      countQuery =
          "SELECT COUNT(*) "
              + "FROM employer_profile e "
              + "JOIN provinces p ON e.province_id = p.code "
              + "WHERE (COALESCE(:name, '') = '' OR e.name LIKE CONCAT('%', :name, '%')) "
              + "AND (COALESCE(:provinceCode, '') = '' OR p.code = :provinceCode)",
      nativeQuery = true)
  Page<FilterEmployerDto[]> filterEmployer(
      @Param("name") String name, @Param("provinceCode") String provinceCode, Pageable pageable);
}
