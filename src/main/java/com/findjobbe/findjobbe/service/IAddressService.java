package com.findjobbe.findjobbe.service;

import com.findjobbe.findjobbe.mapper.dto.DistrictDto;
import com.findjobbe.findjobbe.mapper.dto.ProvinceDto;
import java.util.List;

public interface IAddressService {
  List<ProvinceDto> getAllProvinces();

  List<DistrictDto> getDistrictsByProvinceCode(String provinceCode);
}
