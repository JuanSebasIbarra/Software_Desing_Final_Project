package com.ezyvet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EzyvetApplication {

	public static void main(String[] args) {
		SpringApplication.run(EzyvetApplication.class, args);
	}
}
