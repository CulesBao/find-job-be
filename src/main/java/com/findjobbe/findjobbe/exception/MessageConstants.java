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
  public static final String PASSWORD_NOT_MATCH = "password_not_match";
  public static final String PASSWORD_MUST_BE_DIFFERENT = "password_must_be_different";
  public static final String EMPLOYER_NOT_FOUND = "employer_not_found";
  public static final String MIN_SALARY_MUST_BE_LESS_THAN_MAX_SALARY =
      "min_salary_must_be_less_than_max_salary";
  public static final String JOB_NOT_FOUND = "job_not_found";
  public static final String EXPIRED_AT_MUST_BE_IN_FUTURE = "expired_at_must_be_in_future";
  public static final String NOT_AUTHORIZED_TO_UPDATE_JOB = "not_authorized_to_update_job";
  public static final String APPLICATION_NOT_FOUND = "application_not_found";
  public static final String UNAUTHORIZED_ACCESS = "unauthorized_access";
  public static final String APPLICATION_ALREADY_WITHDRAWN = "application_already_withdrawn";
  public static final String CANDIDATE_NOT_FOLLOWING = "candidate_not_following";
  public static final String EMPLOYER_NOT_FOLLOWING = "employer_not_following";
  public static final String OLD_PASSWORD_NOT_MATCH = "old_password_not_match";
  public static final String JOB_IS_EXPIRED = "job_is_expired";
  public static final String CANDIDATE_ALREADY_APPLIED = "candidate_already_applied";
  public static final String NOT_AUTHORIZED_TO_DELETE_JOB = "not_authorized_to_delete_job";
  public static final String CANDIDATE_ALREADY_FOLLOWING = "candidate_already_following";
  public static final String EMPLOYER_ALREADY_FOLLOWING = "employer_already_following";
}
