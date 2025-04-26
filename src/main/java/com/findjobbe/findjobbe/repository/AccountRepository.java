package com.findjobbe.findjobbe.repository;

import com.findjobbe.findjobbe.enums.Provider;
import com.findjobbe.findjobbe.model.Account;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
  Boolean existsByEmail(String email);

  @Query(
      "SELECT a FROM Account a WHERE a.id = :id AND a.code = :code AND a.expiredAt > CURRENT_TIMESTAMP")
  Optional<Account> findByCode(@Param("id") UUID id, @Param("code") String code);

  Optional<Account> findByEmailAndIsActiveAndProvider(
      @NotNull @NotBlank String email, boolean b, Provider provider);

  boolean existsByEmailAndProvider(String email, Provider provider);

  Optional<Account> findByEmailAndIsActive(String email, boolean b);
}
