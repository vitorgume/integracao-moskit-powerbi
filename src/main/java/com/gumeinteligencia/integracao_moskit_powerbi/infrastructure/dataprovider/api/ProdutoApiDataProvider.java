package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.api;

import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.api.ProdutoGatewayApi;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.ProdutoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@Slf4j
public class ProdutoApiDataProvider implements ProdutoGatewayApi {

    private final WebClient webClient;

    @Value("${moskit.api.key}")
    private final String apiKey;

    @Value("${moskit.api.base-url}")
    private final String baseUrl;

    public ProdutoApiDataProvider(
            WebClient webClient,
            @Value("${moskit.api.key}") String apiKey,
            @Value("${moskit.api.base-url}") String baseUrl
    ){
        this.webClient = webClient;
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
    }

    @Override
    public List<ProdutoDto> consultarProdutos() {
        log.info("Consultando produtos na api...");
        String uri = baseUrl + "/products?quantity=50";

        List<ProdutoDto> produtos = webClient.get()
                .uri(uri)
                .header("apikey", apiKey)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ProdutoDto>>() {})
                .block();

        log.info("Finalizado consulta de produtos na api...");

        return produtos;
    }
}
