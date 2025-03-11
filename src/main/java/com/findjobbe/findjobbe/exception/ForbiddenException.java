package com.findjobbe.findjobbe.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
@Getter
@Setter
public class ForbiddenException extends RuntimeException {

  public ForbiddenException(ErrorResponse error) {
    super(error.getMessage());
  }
}
