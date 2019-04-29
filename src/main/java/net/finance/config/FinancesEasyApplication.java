package net.finance.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "net.finance" })
@EntityScan(basePackages = { "net.finance.entity" })
@EnableJpaRepositories(basePackages = { "net.finance.repository" })
@EnableJpaAuditing
public class FinancesEasyApplication {

	public static void main(final String[] args) {
		SpringApplication.run(FinancesEasyApplication.class, args);
	}
}
