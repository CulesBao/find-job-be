package com.findjobbe.findjobbe.mapper.request;

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
    private UUID applicationId;
    private String status;
  }
}
