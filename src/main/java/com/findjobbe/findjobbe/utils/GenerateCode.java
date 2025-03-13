package com.findjobbe.findjobbe.utils;

import org.springframework.stereotype.Component;

@Component
public class GenerateCode {
  public String generateCode() {
    String code = "";
    for (int i = 0; i < 5; i++) {
      code += (int) (Math.random() * 10);
    }
    return code;
  }
}
