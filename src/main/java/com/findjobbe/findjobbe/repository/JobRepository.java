package com.findjobbe.findjobbe.repository;

import com.findjobbe.findjobbe.mapper.dto.GetEmployerJobsDto;
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
              + " ORDER BY j.expired_at ASC",
      countQuery =
          "SELECT COUNT(DISTINCT j.id) FROM employer_profile e "
              + "JOIN job j ON e.id = j.employer_id "
              + "WHERE e.id = :employerId",
      nativeQuery = true)
  Page<GetEmployerJobsDto[]> getAllEmployerJobsRaw(
      @Param("employerId") UUID employerId, Pageable pageable);
}
