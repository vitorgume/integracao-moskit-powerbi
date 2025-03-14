package com.gumeinteligencia.integracao_moskit_powerbi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl("https://api.moskitcrm.com/v2")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer SEU_TOKEN_AQUI")
                .build();
    }
}
