package com.findjobbe.findjobbe.exception;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class MessageConstants {
  public static final String EMAIL_ALREADY_EXISTS = "email_already_exists";
  public static final String INVALID_CODE = "invalid_code";
  public static final String LOGIN_FAILED = "login_failed";
  public static final String ACCOUNT_NOT_FOUND = "account_not_found";
  public static final String WRONG_PROVINCE_CODE = "wrong_province_code";
  public static final String PROFILE_NOT_FOUND = "profile_not_found";
  public static final String PROVINCE_NOT_FOUND = "province_not_found";
  public static final String DISTRICT_NOT_FOUND = "district_not_found";
  public static final String IMAGE_UPLOAD_FAILED = "image_upload_failed";
  public static final String PROFILE_ALREADY_EXISTS = "profile_already_exists";
  public static final String UNSUPPORTED_FILE_TYPE = "unsupported_file_type";
}
