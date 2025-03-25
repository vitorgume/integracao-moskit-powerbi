package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.api;

import com.gumeinteligencia.integracao_moskit_powerbi.application.service.dto.FunilDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Slf4j
@Component
public class FunisApiDataProvider {

    private final WebClient webClient;

    @Value("${moskit.api.key}")
    private final String apiKey;

    @Value("${moskit.api.base-url}")
    private final String baseUrl;

    public FunisApiDataProvider(
            WebClient webClient,
            @Value("${moskit.api.key}") String apiKey,
            @Value("${moskit.api.base-url}") String baseUrl
    ){
        this.webClient = webClient;
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
    }

    public List<FunilDto> consultarFunis() {
        log.info("Consultando funis na api...");
        String uri = baseUrl + "/pipelines";

        List<FunilDto> funis = webClient.get()
                .uri(uri)
                .header("apikey", apiKey)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<FunilDto>>() {})
                .block();

        log.info("Finalizado consulta de funis da api...");
        return funis;
    }
}
