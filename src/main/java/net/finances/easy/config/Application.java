package net.finances.easy.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "net.finances.easy" })
public class Application {

	public static void main(final String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
