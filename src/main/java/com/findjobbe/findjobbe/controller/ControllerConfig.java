package com.findjobbe.findjobbe.controller;

import com.findjobbe.findjobbe.exception.MessageConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@AllArgsConstructor
@Configuration
public class ControllerConfig {
  private final MessageConstants messageConstants;

  //  @Bean
  //  public SampleController sampleController() {
  //    return new SampleController(messageConstants);
  //  }
}