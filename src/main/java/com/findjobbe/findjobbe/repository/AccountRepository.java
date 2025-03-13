package com.findjobbe.findjobbe.repository;

import com.findjobbe.findjobbe.model.Account;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
  Optional<Account> findByEmail(String email);

  Boolean existsByEmail(String email);
}
