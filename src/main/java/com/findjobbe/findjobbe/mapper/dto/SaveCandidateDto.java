package com.findjobbe.findjobbe.mapper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

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
}
