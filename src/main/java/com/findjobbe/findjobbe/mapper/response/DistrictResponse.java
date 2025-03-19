package com.findjobbe.findjobbe.mapper.response;

import com.findjobbe.findjobbe.mapper.dto.DistrictDto;
import java.util.List;

public class DistrictResponse {
  public DistrictResponse(List<DistrictDto> districts) {
    this.districts = districts;
  }

  private List<DistrictDto> districts;
}
