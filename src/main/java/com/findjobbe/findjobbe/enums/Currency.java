package com.findjobbe.findjobbe.enums;

public enum Currency {
  USD("United States Dollar"),
  EUR("Euro"),
  JPY("Japanese Yen"),
  GBP("British Pound Sterling"),
  AUD("Australian Dollar"),
  CAD("Canadian Dollar"),
  CHF("Swiss Franc"),
  CNY("Chinese Yuan"),
  HKD("Hong Kong Dollar"),
  SGD("Singapore Dollar"),
  KRW("South Korean Won"),
  INR("Indian Rupee"),
  VND("Vietnamese Dong");

  private final String fullName;

  Currency(String fullName) {
    this.fullName = fullName;
  }

  public String getFullName() {
    return fullName;
  }
}
