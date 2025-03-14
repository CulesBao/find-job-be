package com.findjobbe.findjobbe.repository;

import com.findjobbe.findjobbe.model.Account;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
  Optional<Account> findByEmail(String email);

  Boolean existsByEmail(String email);

  @Query(
      "SELECT a FROM Account a WHERE a.id = :id AND a.code = :code AND a.expiredAt > CURRENT_TIMESTAMP")
  Optional<Account> findByCode(@Param("id") UUID id, @Param("code") String code);
}
