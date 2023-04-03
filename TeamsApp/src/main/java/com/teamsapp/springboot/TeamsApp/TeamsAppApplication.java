package com.teamsapp.springboot.TeamsApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.teamsapp.springboot.TeamsApp.dao")
public class TeamsAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamsAppApplication.class, args);
	}

}
