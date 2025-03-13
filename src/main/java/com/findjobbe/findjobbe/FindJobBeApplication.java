package com.findjobbe.findjobbe;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.findjobbe.findjobbe.*"})
@EnableJpaRepositories(basePackages = {"com.findjobbe.findjobbe.*"})
@ComponentScan(basePackages = {"com.findjobbe.findjobbe.*"})
public class FindJobBeApplication {
  static {
    Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
    dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
  }

    public static void main(String[] args) {
        SpringApplication.run(FindJobBeApplication.class, args);
    }
}
