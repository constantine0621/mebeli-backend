package com.dosev.mebeli;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MebeliApplication {

	public static void main(String[] args) {
		SpringApplication.run(MebeliApplication.class, args);
	}

	@Bean
	public CommandLineRunner testCategories() {
		return args -> {

		};
	}

}
