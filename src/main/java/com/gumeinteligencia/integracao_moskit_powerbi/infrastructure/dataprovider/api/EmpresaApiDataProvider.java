package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.api;

import com.gumeinteligencia.integracao_moskit_powerbi.application.service.dto.EmpresaDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class EmpresaApiDataProvider {

    private final WebClient webClient;

    @Value("${moskit.api.key}")
    private final String apiKey;

    @Value("${moskit.api.base-url}")
    private final String baseUrl;

    public EmpresaApiDataProvider(
            WebClient webClient,
            @Value("${moskit.api.key}") String apiKey,
            @Value("${moskit.api.base-url}") String baseUrl
    ){
        this.webClient = webClient;
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
    }

    public List<EmpresaDto> consultarEmpresas() {
        log.info("Consultando empresas na api...");
        String uri = baseUrl + "/companies/search";
        String nextPageToken = null;
        int quantity = 50;
        List<EmpresaDto> todasEmpresas = new ArrayList<>();

        String dataDeConsulta = GeradorDeData.gerarData();

        List<Map<String, Object>> requestBody = List.of(
                Map.of(
                        "field", "dateCreated",
                        "expression", "gte",
                        "values", List.of(dataDeConsulta)
                )
        );

        do {
            String uriPaginada = nextPageToken == null
                    ? uri + "?quantity=" + quantity
                    : uri + "?quantity=" + quantity + "&nextPageToken=" + nextPageToken;

            var response = webClient.post()
                    .uri(uriPaginada)
                    .header("apikey", apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .toEntityList(EmpresaDto.class)
                    .block();

            if (response != null && response.getBody() != null) {
                todasEmpresas.addAll(response.getBody());
            }

            nextPageToken = response != null ? response.getHeaders().getFirst("X-Moskit-Listing-Next-Page-Token") : null;

        } while (nextPageToken != null && !nextPageToken.isEmpty());

        log.info("Finalizado consulta de empresas na api...");
        return todasEmpresas;
    }
}
