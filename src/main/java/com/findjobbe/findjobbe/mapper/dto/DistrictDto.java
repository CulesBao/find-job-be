package com.findjobbe.findjobbe.mapper.dto;

import com.findjobbe.findjobbe.model.District;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DistrictDto {
  public DistrictDto(District district) {
    this.code = district.getCode();
    this.name = district.getName();
    this.nameEn = district.getNameEn();
    this.fullName = district.getFullName();
    this.fullNameEn = district.getFullNameEn();
  }

    private String code;
    private String name;
    private String nameEn;
    private String fullName;
    private String fullNameEn;
}
