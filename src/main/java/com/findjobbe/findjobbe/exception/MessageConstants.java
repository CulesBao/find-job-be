package com.findjobbe.findjobbe.exception;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class MessageConstants {
  public static final String EMAIL_ALREADY_EXISTS = "email_already_exists";
  public static final String INVALID_CODE = "invalid_code";
}
