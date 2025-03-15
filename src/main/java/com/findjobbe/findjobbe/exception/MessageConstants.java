package com.findjobbe.findjobbe.exception;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class MessageConstants {
  public static final String EMAIL_ALREADY_EXISTS = "email_already_exists";
  public static final String INVALID_CODE = "invalid_code";
  public static final String LOGIN_FAILED = "login_failed";
  public static final String ACCOUNT_NOT_ACTIVE = "account_not_active";
  public static final String ACCOUNT_NOT_FOUND = "account_not_found";
}
