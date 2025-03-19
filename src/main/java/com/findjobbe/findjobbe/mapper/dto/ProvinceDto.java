package com.findjobbe.findjobbe.mapper.dto;

import com.findjobbe.findjobbe.model.Province;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class ProvinceDto {
  public ProvinceDto(Province province) {
    this.code = province.getCode();
    this.name = province.getName();
    this.nameEn = province.getNameEn();
    this.fullName = province.getFullName();
    this.fullNameEn = province.getFullNameEn();
  }

    private String code;
    private String name;
    private String nameEn;
    private String fullName;
    private String fullNameEn;
}
