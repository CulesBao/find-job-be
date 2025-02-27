package com.findjobbe.findjobbe;

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
    public static void main(String[] args) {
        SpringApplication.run(FindJobBeApplication.class, args);
    }
}
