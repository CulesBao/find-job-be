package com.findjobbe.findjobbe.mapper.response;

import com.findjobbe.findjobbe.mapper.dto.ProvinceDto;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProvinceResponse {
  public ProvinceResponse(List<ProvinceDto> provinces) {
    this.provinces = provinces;
  }

  private List<ProvinceDto> provinces;
}
