package com.example.study308;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Study308Application {

    public static void main(String[] args) {
        SpringApplication.run(Study308Application.class, args);
    }

}
