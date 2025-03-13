package com.findjobbe.findjobbe.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfiguration {
  @Bean
  public Dotenv dotenv() {
    return Dotenv.configure().directory("src").ignoreIfMissing().ignoreIfMalformed().load();
  }
}
