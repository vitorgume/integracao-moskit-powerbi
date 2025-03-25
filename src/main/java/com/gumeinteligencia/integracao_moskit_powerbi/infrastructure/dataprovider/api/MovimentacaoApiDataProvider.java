package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.api;

import com.gumeinteligencia.integracao_moskit_powerbi.application.service.dto.MovimentacaoNegociosDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class MovimentacaoApiDataProvider {

    private final WebClient webClient;

    @Value("${moskit.api.key}")
    private final String apiKey;

    @Value("${moskit.api.base-url}")
    private final String baseUrl;

    public MovimentacaoApiDataProvider(
            WebClient webClient,
            @Value("${moskit.api.key}") String apiKey,
            @Value("${moskit.api.base-url}") String baseUrl
    ){
        this.webClient = webClient;
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
    }

    public List<MovimentacaoNegociosDto> consultarMovimentacoes(Long idNegocio) {
        log.info("Consultando movimentações na API...");

        String uri = baseUrl + "/deals/" + idNegocio + "/movements";
        String nextPageToken = null;
        int quantity = 50;
        List<MovimentacaoNegociosDto> todasMovimentacoes = new ArrayList<>();

        do {
            String uriPaginada = nextPageToken == null
                    ? uri + "?quantity=" + quantity
                    : uri + "?quantity=" + quantity + "&nextPageToken=" + nextPageToken;

            var response = webClient.get()
                    .uri(uriPaginada)
                    .header("apikey", apiKey)
                    .retrieve()
                    .toEntityList(MovimentacaoNegociosDto.class)
                    .block();

            if (response != null && response.getBody() != null) {
                todasMovimentacoes.addAll(response.getBody());
            }

            nextPageToken = response != null ? response.getHeaders().getFirst("X-Moskit-Listing-Next-Page-Token") : null;
        } while (nextPageToken != null && !nextPageToken.isEmpty());

        log.info("Finalizado consultas de movimentações...");

        return todasMovimentacoes;
    }

}
