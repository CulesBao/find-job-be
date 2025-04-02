package com.findjobbe.findjobbe.mapper.dto;

import com.findjobbe.findjobbe.enums.JobProccess;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDto {
    private UUID id;
    private String cover_letter;
    private String cv_url;
    private JobProccess jobProccess;
    private CandidateProfileDto candidateProfile;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
