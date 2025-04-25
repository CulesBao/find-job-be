package com.findjobbe.findjobbe.repository;

import com.findjobbe.findjobbe.mapper.dto.CandidateAppliedJobs;
import com.findjobbe.findjobbe.mapper.dto.EmployerAppliedJobs;
import com.findjobbe.findjobbe.model.Application;
import com.findjobbe.findjobbe.model.CandidateProfile;
import com.findjobbe.findjobbe.model.Job;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {
  @Query(
      value =
          "SELECT a.id AS applicationId, "
              + " e.logo_url AS logoUrl, "
              + " e.name, "
              + " p.name_en AS location, "
              + " j.title, "
              + " j.min_salary AS minSalary, "
              + " j.max_salary AS maxSalary, "
              + " j.currency, "
              + " CAST(j.expired_at AS TIMESTAMP) AS expiredAt, "
              + " a.proccess AS jobProccess, "
              + " j.id AS jobId"
              + " FROM job j "
              + " JOIN application a ON j.id = a.job_id "
              + " JOIN employer_profile e ON j.employer_id = e.id "
              + " JOIN provinces p ON e.province_id = p.code "
              + " JOIN candidate_profile c ON a.candidate_profile_id = c.id "
              + " WHERE c.id = :candidateProfileId",
      countQuery =
          "SELECT COUNT(*) "
              + " FROM job j "
              + " JOIN application a ON j.id = a.job_id "
              + " JOIN employer_profile e ON j.employer_id = e.id "
              + " JOIN provinces p ON e.province_id = p.code "
              + " JOIN candidate_profile c ON a.candidate_profile_id = c.id "
              + " WHERE c.id = :candidateProfileId",
      nativeQuery = true)
  Page<CandidateAppliedJobs[]> findAllByCandidateProfileId(
      @Param("candidateProfileId") UUID candidateProfileId, Pageable pageable);

  @Query(
      value =
          "SELECT a.id, c.first_name AS firstName, c.last_name AS lastName, c.avatar_url AS avatarUrl, c.education, a.created_at AS createdAt, a.proccess AS jobProccess"
              + " FROM application a"
              + " JOIN job j ON a.job_id = j.id"
              + " JOIN candidate_profile c ON a.candidate_profile_id = c.id"
              + " JOIN employer_profile e ON j.employer_id = e.id"
              + " WHERE e.id = :employerId AND j.id = :jobId"
              + " AND (COALESCE(:jobProcess, '') = '' OR a.proccess = :jobProcess)",
      countQuery =
          "SELECT COUNT(*) FROM application a"
              + " JOIN job j ON a.job_id = j.id"
              + " JOIN candidate_profile c ON a.candidate_profile_id = c.id"
              + " JOIN employer_profile e ON j.employer_id = e.id"
              + " WHERE e.id = :employerId AND j.id = :jobId"
              + " AND (COALESCE(:jobProcess, '') = '' OR a.proccess = :jobProcess)",
      nativeQuery = true)
  Page<EmployerAppliedJobs[]> getEmployerAppliedJobs(
      @Param("employerId") UUID employerId,
      @Param("jobProcess") String jobProcess,
      @Param("jobId") UUID jobId,
      Pageable pageable);

  List<Application> findAllByJobId(UUID uuid);

  Application findApplicationByJobAndCandidateProfile(Job job, CandidateProfile candidateProfile);
}
