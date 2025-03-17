package com.findjobbe.findjobbe.mapper.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class DistrictDto {
    private String code;
    private String name;
    private String nameEn;
    private String fullName;
    private String fullNameEn;
}
