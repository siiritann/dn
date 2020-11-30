package com.example.datanor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DatanorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatanorApplication.class, args);
	}

}
