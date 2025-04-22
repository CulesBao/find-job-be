package com.findjobbe.findjobbe.repository;

import com.findjobbe.findjobbe.mapper.dto.EmployerJobsDto;
import com.findjobbe.findjobbe.mapper.dto.FilterJobsDto;
import com.findjobbe.findjobbe.model.Job;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, UUID> {
  @Query(
      value =
          "SELECT j.id, j.title, j.job_type, j.expired_at, COUNT(a.id) "
              + "FROM employer_profile e "
              + "JOIN job j ON e.id = j.employer_id "
              + "LEFT JOIN application a ON j.id = a.job_id "
              + "WHERE e.id = :employerId "
              + "GROUP BY j.id, j.title, j.job_type, j.expired_at"
              + " ORDER BY j.expired_at DESC",
      countQuery =
          "SELECT COUNT(DISTINCT j.id) FROM employer_profile e "
              + "JOIN job j ON e.id = j.employer_id "
              + "WHERE e.id = :employerId",
      nativeQuery = true)
  Page<EmployerJobsDto[]> getAllEmployerJobsRaw(
      @Param("employerId") UUID employerId, Pageable pageable);

  @Query(
      value =
          "SELECT j.id, e.logo_url AS logoUrl, e.name, p.name_en AS location, j.title, "
              + " j.min_salary AS minSalary, j.max_salary AS maxSalary, j.currency, j.expired_at AS expiredAt "
              + " FROM employer_profile e "
              + " JOIN job j ON e.id = j.employer_id "
              + " JOIN provinces p ON e.province_id = p.code "
              + " WHERE (COALESCE(:title, '') = '' OR j.title LIKE CONCAT('%', :title, '%')) "
              + " AND (COALESCE(:provinceCode, '') = '' OR p.code = :provinceCode) "
              + " AND (COALESCE(:jobType, '') = '' OR j.job_type = :jobType) "
              + " AND (COALESCE(:education, '') = '' OR j.education = :education) "
              + " AND (COALESCE(:minSalary, 0) = 0 OR j.min_salary >= :minSalary) "
              + " AND (COALESCE(:maxSalary, 0) = 0 OR j.max_salary <= :maxSalary) "
              + " AND (COALESCE(:currency, '') = '' OR j.currency = :currency) "
              + " AND (COALESCE(:salaryType, '') = '' OR j.salary_type = :salaryType) "
              + " AND j.expired_at >= CURRENT_DATE "
              + " ORDER BY j.expired_at DESC ",
      countQuery =
          "SELECT COUNT(*) FROM employer_profile e "
              + " JOIN job j ON e.id = j.employer_id "
              + " JOIN provinces p ON e.province_id = p.code "
              + " WHERE (COALESCE(:title, '') = '' OR j.title LIKE CONCAT('%', :title, '%')) "
              + " AND (COALESCE(:provinceCode, '') = '' OR p.code = :provinceCode) "
              + " AND (COALESCE(:jobType, '') = '' OR j.job_type = :jobType) "
              + " AND (COALESCE(:education, '') = '' OR j.education = :education) "
              + " AND (COALESCE(:minSalary, 0) = 0 OR j.min_salary >= :minSalary) "
              + " AND (COALESCE(:maxSalary, 0) = 0 OR j.max_salary <= :maxSalary) "
              + " AND (COALESCE(:currency, '') = '' OR j.currency = :currency) "
              + " AND (COALESCE(:salaryType, '') = '' OR j.salary_type = :salaryType) "
              + " AND j.expired_at >= CURRENT_DATE ",
      nativeQuery = true)
  Page<FilterJobsDto[]> filterJobs(
      @Param("title") String title,
      @Param("provinceCode") String provinceCode,
      @Param("jobType") String jobType,
      @Param("education") String education,
      @Param("minSalary") Double minSalary,
      @Param("maxSalary") Double maxSalary,
      @Param("currency") String currency,
      @Param("salaryType") String salaryType,
      Pageable pageable);
}
