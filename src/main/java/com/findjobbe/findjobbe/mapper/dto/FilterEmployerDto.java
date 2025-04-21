package com.findjobbe.findjobbe.mapper.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilterEmployerDto {
  private UUID id;
  private String name;
  private String logoUrl;
  private String location;
  private int jobCount;
}
