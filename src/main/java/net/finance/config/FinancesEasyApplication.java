package net.finance.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = { "net.finance" })
@EntityScan(basePackages = { "net.finance.entity" })
@SpringBootApplication
public class FinancesEasyApplication {

	public static void main(final String[] args) {
		SpringApplication.run(FinancesEasyApplication.class, args);
	}
}
