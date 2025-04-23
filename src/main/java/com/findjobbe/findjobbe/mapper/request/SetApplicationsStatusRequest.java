package com.findjobbe.findjobbe.mapper.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SetApplicationsStatusRequest {
  private List<SetApplicationStatus> applications;

  @Getter
  @Setter
  public static class SetApplicationStatus {
    @NotNull @NotBlank private UUID applicationId;
    @NotNull @NotBlank private String status;
  }
}
