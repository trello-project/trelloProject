package com.example.trelloproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
public class TrelloProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(TrelloProjectApplication.class, args);
    }
}
