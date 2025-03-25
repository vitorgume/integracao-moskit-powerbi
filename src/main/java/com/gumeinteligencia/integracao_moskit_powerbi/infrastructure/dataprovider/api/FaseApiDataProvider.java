package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.api;

import com.gumeinteligencia.integracao_moskit_powerbi.application.service.dto.FaseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class FaseApiDataProvider {

    private final WebClient webClient;

    @Value("${moskit.api.key}")
    private final String apiKey;

    @Value("${moskit.api.base-url}")
    private final String baseUrl;

    public FaseApiDataProvider(
            WebClient webClient,
            @Value("${moskit.api.key}") String apiKey,
            @Value("${moskit.api.base-url}") String baseUrl
    ){
        this.webClient = webClient;
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
    }

    public List<FaseDto> consultaFases() {
        log.info("Consultando fases na api...");
        String uri = baseUrl + "/stages";
        String nextPageToken = null;
        int quantity = 50;
        List<FaseDto> todasFases = new ArrayList<>();

        do {
            String uriPaginada = nextPageToken == null
                    ? uri + "?quantity=" + quantity
                    : uri + "?quantity=" + quantity + "&nextPageToken=" + nextPageToken;

            var response = webClient.get()
                    .uri(uriPaginada)
                    .header("apikey", apiKey)
                    .retrieve()
                    .toEntityList(FaseDto.class)
                    .block();

            if (response != null && response.getBody() != null) {
                todasFases.addAll(response.getBody());
            }

            nextPageToken = response != null ? response.getHeaders().getFirst("X-Moskit-Listing-Next-Page-Token") : null;

        } while (nextPageToken != null && !nextPageToken.isEmpty());

        log.info("Finalizado consulta de fases da api...");

        return todasFases;
    }

}
