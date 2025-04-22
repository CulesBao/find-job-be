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
public class SaveCandidateDto extends BaseProfile {
  private UUID id;
  private String firstName;
  private String lastName;
  private String location;
  private String avatarUrl;
  private Boolean gender;
}
