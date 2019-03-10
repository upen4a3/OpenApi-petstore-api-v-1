package com.pet.operations;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.pet.operations")
@ComponentScan(basePackages = "com.pet.operations")
public class PetFinderApiV1Application {
	private static ConfigurableApplicationContext context;

	public static void main(String[] args) {
		SpringApplication.run(PetFinderApiV1Application.class, args);
	}

	public static void restart() {
		ApplicationArguments args = context.getBean(ApplicationArguments.class);

		Thread thread = new Thread(() -> {
			context.close();
			context = SpringApplication.run(PetFinderApiV1Application.class, args.getSourceArgs());
		});

		thread.setDaemon(false);
		thread.start();
	}
}
