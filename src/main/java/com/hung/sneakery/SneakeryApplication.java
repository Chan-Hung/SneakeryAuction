package com.hung.sneakery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SneakeryApplication {

    public static void main(String[] args) {
        SpringApplication.run(SneakeryApplication.class, args);
    }
}
