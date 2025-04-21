package com.findjobbe.findjobbe.mapper.request;

import com.findjobbe.findjobbe.mapper.dto.BaseProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilterCandidateRequest extends BaseProfile {
  private String firstName;
  private String lastName;
  private String education;
  private String provinceCode;
  private String gender;
}
