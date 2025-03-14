package com.gumeinteligencia.integracao_moskit_powerbi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class IntegracaoMoskitPowerbiApplication {
	public static void main(String[] args) {
		SpringApplication.run(IntegracaoMoskitPowerbiApplication.class, args);
	}

}
