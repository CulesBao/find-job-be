package com.findjobbe.findjobbe.mapper.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FilterCandidateDto {
  private UUID id;
  private String firstName;
  private String lastName;
  private String avatarUrl;
  private String location;
  private String education;
  private boolean gender;
}
