package com.findjobbe.findjobbe.service.impl;

import com.findjobbe.findjobbe.exception.MessageConstants;
import com.findjobbe.findjobbe.exception.NotFoundException;
import com.findjobbe.findjobbe.mapper.dto.DistrictDto;
import com.findjobbe.findjobbe.mapper.dto.ProvinceDto;
import com.findjobbe.findjobbe.model.District;
import com.findjobbe.findjobbe.model.Province;
import com.findjobbe.findjobbe.repository.DistrictRepository;
import com.findjobbe.findjobbe.repository.ProvinceRepository;
import com.findjobbe.findjobbe.service.IAddressService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements IAddressService {
  private final ProvinceRepository provinceRepository;
  private final DistrictRepository districtRepository;

  @Autowired
  public AddressServiceImpl(
      ProvinceRepository provinceRepository, DistrictRepository districtRepository) {
    this.provinceRepository = provinceRepository;
    this.districtRepository = districtRepository;
  }

  @Override
  public List<ProvinceDto> getAllProvinces() {
    List<Province> provinces = provinceRepository.findAll();
    List<ProvinceDto> provinceDtos = new ArrayList<>();
    for (Province province : provinces) {
      provinceDtos.add(new ProvinceDto(province));
    }
    return provinceDtos;
  }

  @Override
  public List<DistrictDto> getDistrictsByProvinceCode(String provinceCode) {
    List<District> districts = districtRepository.findAllByProvinceCode(provinceCode);
    if (districts.isEmpty()) {
      throw new NotFoundException(MessageConstants.WRONG_PROVINCE_CODE);
    }
    List<DistrictDto> districtDtos = new ArrayList<>();
    for (District district : districts) {
      districtDtos.add(new DistrictDto(district));
    }
    return districtDtos;
  }
}
