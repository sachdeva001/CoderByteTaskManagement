package com.coderbyte.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskManagementApplication {

	public static void main(String[] args) {
		/**
		 * Entry point for the Task Management Spring Boot application.
		 * Starts the embedded server and initializes the Spring context.
		 *
		 * @param args command-line arguments
		 */
		SpringApplication.run(TaskManagementApplication.class, args);
	}

}
