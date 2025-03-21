package com.findjobbe.findjobbe.repository;

import com.findjobbe.findjobbe.model.Province;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, UUID> {
  Optional<Province> findByCode(String code);
}
