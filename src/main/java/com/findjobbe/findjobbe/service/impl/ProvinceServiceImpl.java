package com.findjobbe.findjobbe.service.impl;

import com.findjobbe.findjobbe.exception.MessageConstants;
import com.findjobbe.findjobbe.exception.NotFoundException;
import com.findjobbe.findjobbe.model.Province;
import com.findjobbe.findjobbe.repository.ProvinceRepository;
import org.springframework.stereotype.Service;

@Service
public class ProvinceServiceImpl {
  private final ProvinceRepository provinceRepository;

  public ProvinceServiceImpl(ProvinceRepository provinceRepository) {
    this.provinceRepository = provinceRepository;
  }

  public Province getProvinceByCode(String code) {
    return provinceRepository
        .findByCode(code)
        .orElseThrow(() -> new NotFoundException(MessageConstants.PROVINCE_NOT_FOUND));
  }
}
