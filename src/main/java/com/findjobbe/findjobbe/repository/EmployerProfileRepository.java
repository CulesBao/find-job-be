package com.findjobbe.findjobbe.repository;

import com.findjobbe.findjobbe.model.EmployerProfile;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployerProfileRepository extends JpaRepository<EmployerProfile, UUID> {
  Optional<EmployerProfile> findByAccountId(UUID uuid);

  boolean existsByAccountId(UUID uuid);
}
