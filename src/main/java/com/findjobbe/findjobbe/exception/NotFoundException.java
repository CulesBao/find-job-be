package com.findjobbe.findjobbe.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
@Setter
public class NotFoundException extends RuntimeException {
  public NotFoundException(String error) {
    super(error);
  }
}
