package com.findjobbe.findjobbe.service.impl;

import com.findjobbe.findjobbe.exception.MessageConstants;
import com.findjobbe.findjobbe.exception.NotFoundException;
import com.findjobbe.findjobbe.model.District;
import com.findjobbe.findjobbe.repository.DistrictRepository;
import org.springframework.stereotype.Service;

@Service
public class DistrictServiceImpl {
  private final DistrictRepository districtRepository;

  public DistrictServiceImpl(DistrictRepository districtRepository) {
    this.districtRepository = districtRepository;
  }

  public District getDistrictByCodeAndProvinceCode(String code, String provinceCode) {
    return districtRepository
        .findByCodeAndProvinceCode(code, provinceCode)
        .orElseThrow(() -> new NotFoundException(MessageConstants.DISTRICT_NOT_FOUND));
  }
}
