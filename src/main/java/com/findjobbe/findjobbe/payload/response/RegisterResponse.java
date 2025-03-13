package com.findjobbe.findjobbe.payload.response;

import java.util.Optional;

public class RegisterResponse extends AbstractResponse {

  public RegisterResponse(String message, Optional<Object> data) {
    super(message, data);
  }
}
