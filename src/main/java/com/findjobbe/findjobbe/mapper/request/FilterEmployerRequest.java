package com.findjobbe.findjobbe.mapper.request;

import com.findjobbe.findjobbe.mapper.dto.BaseProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FilterEmployerRequest extends BaseProfile {
  private String name;
  private String provinceCode;
}
