package com.hung.sneakery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SneakeryApplication {
	public static void main(String[] args) {
		SpringApplication.run(SneakeryApplication.class, args);
	}

}
