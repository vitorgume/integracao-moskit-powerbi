package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.api;

import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.api.AtividadeGatewayApi;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.api.exceptions.DataProviderApiException;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.AtividadeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class AtividadeApiDataProvider implements AtividadeGatewayApi {

    private final WebClient webClient;

    @Value("${moskit.api.key}")
    private final String apiKey;

    @Value("${moskit.api.base-url}")
    private final String baseUrl;

    public AtividadeApiDataProvider(
        WebClient webClient,
        @Value("${moskit.api.key}") String apiKey,
        @Value("${moskit.api.base-url}") String baseUrl
    ){
        this.webClient = webClient;
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
    }


    @Override
    public List<AtividadeDto> consultaAtividades() {
        log.info("Consultando atividades na API...");

        String uri = baseUrl + "/activities/search";

        List<Map<String, Object>> requestBody = List.of(
                Map.of(
                        "field", "dateCreated",
                        "expression", "gte",
                        "values", List.of("2025-01-01T00:00:00Z")
                )
        );

        var response = webClient.post()
                .uri(uri)
                .header("apikey", apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .toEntityList(AtividadeDto.class)
                .block();

        if(response == null || response.getBody() == null) {
            throw new DataProviderApiException("Nenhuma atividada encontrada.");
        }

        log.info("Finalizado consultas de atividades...");

        return response.getBody();
    }


}
