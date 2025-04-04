package com.gumeinteligencia.integracao_moskit_powerbi;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IntegracaoMoskitPowerbiApplication {
	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.load();
		System.setProperty("MOSKIT_API_KEY", dotenv.get("MOSKIT_API_KEY"));
		System.setProperty("MOSKIT_API_BASE_URL", dotenv.get("MOSKIT_API_BASE_URL"));
		System.setProperty("BANCO_DADOS_URL", dotenv.get("BANCO_DADOS_URL"));
		System.setProperty("USER_BD", dotenv.get("USER_BD"));
		System.setProperty("PASSWORD_BD", dotenv.get("PASSWORD_BD"));
		System.setProperty("STAGE_COD_FILTRO", dotenv.get("STAGE_COD_FILTRO"));
		System.setProperty("STAGE_COD_ATUAL", dotenv.get("STAGE_COD_ATUAL"));
		System.setProperty("STAGE_COD_ANTIGO", dotenv.get("STAGE_COD_ANTIGO"));
		System.setProperty("CAMPO_PERSONALIZADO_COD_FILTRO", dotenv.get("CAMPO_PERSONALIZADO_COD_FILTRO"));
		System.setProperty("SEGMENTO_EDUCACAO_COD", dotenv.get("SEGMENTO_EDUCACAO_COD"));
		System.setProperty("SEGMENTO_INDUSTRIA_COD", dotenv.get("SEGMENTO_INDUSTRIA_COD"));
		System.setProperty("SEGMENTO_TECNOLOGIA_COD", dotenv.get("SEGMENTO_TECNOLOGIA_COD"));
		System.setProperty("SEGMENTO_VAREJO_COD", dotenv.get("SEGMENTO_VAREJO_COD"));
		System.setProperty("SEGMENTO_PRESTADORES_DE_SERVICO_COD", dotenv.get("SEGMENTO_PRESTADORES_DE_SERVICO_COD"));
		System.setProperty("SEGMENTO_AGENCIA_MARKETING_COD", dotenv.get("SEGMENTO_AGENCIA_MARKETING_COD"));
		System.setProperty("SEGMENTO_JOGOS_COD", dotenv.get("SEGMENTO_JOGOS_COD"));
		System.setProperty("SEGMENTO_CLINICA_MEDICA_COD", dotenv.get("SEGMENTO_CLINICA_MEDICA_COD"));

		SpringApplication.run(IntegracaoMoskitPowerbiApplication.class, args);
	}

}
