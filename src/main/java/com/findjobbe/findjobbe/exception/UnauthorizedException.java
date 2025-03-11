package com.findjobbe.findjobbe.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
@Getter
@Setter
public class UnauthorizedException extends RuntimeException {

  public UnauthorizedException(ErrorResponse error) {
    super(error.getMessage());
  }
}
