package com.findjobbe.findjobbe.exception;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class MessageConstants {
  ErrorResponse Unauthorized =
      new ErrorResponse("Unauthorized", "You are not authorized to access this resource");
}
