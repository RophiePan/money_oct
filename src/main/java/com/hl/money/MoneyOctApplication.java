package com.hl.money;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.hl.money")
public class MoneyOctApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneyOctApplication.class, args);
	}
}
