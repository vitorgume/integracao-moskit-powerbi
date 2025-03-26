package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.api;

import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.api.NegocioGatewayApi;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.NegocioDto;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.api.exceptions.DataProviderApiException;
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
public class NegocioApiDataProvider implements NegocioGatewayApi {

    private final WebClient webClient;

    @Value("${moskit.api.key}")
    private final String apiKey;

    @Value("${moskit.api.base-url}")
    private final String baseUrl;

    public NegocioApiDataProvider(
            WebClient webClient,
            @Value("${moskit.api.key}") String apiKey,
            @Value("${moskit.api.base-url}") String baseUrl
    ){
        this.webClient = webClient;
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
    }

    @Override
    public List<NegocioDto> consultaNegocios() {
        log.info("Consultando negócios na api...");
        String uri = baseUrl + "/deals/search";
        String nextPageToken = null;
        int quantity = 50;
        List<NegocioDto> todosNegocios = new ArrayList<>();

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
                    .toEntityList(NegocioDto.class)
                    .block();



            if (response != null && response.getBody() != null) {
                todosNegocios.addAll(response.getBody());
            }

            nextPageToken = response != null ? response.getHeaders().getFirst("X-Moskit-Listing-Next-Page-Token") : null;
        } while (nextPageToken != null && !nextPageToken.isEmpty());

        log.info("Finalizado consultas de negócios na api...");
        return todosNegocios;
    }

    @Override
    public NegocioDto consultarPorId(Integer id) {
        log.info("Consultando negócio por id na api...");
        String uri = baseUrl + "/deals/" + id;

        var response = webClient.get()
                .uri(uri)
                .header("apikey", apiKey)
                .retrieve()
                .toEntityList(NegocioDto.class)
                .block();

        if(response == null) {
            throw new DataProviderApiException("Negócio não encontrado na API.");
        }

        log.info("Negócio consultado com sucesso...");

        return response.getBody().get(0);
    }
}
