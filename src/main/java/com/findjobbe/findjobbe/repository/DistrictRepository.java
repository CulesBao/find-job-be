package com.findjobbe.findjobbe.repository;

import com.findjobbe.findjobbe.model.District;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends JpaRepository<District, UUID> {
  List<District> findAllByProvinceCode(String provinceCode);

  Optional<District> findByCodeAndProvinceCode(String code, String provinceCode);
}
