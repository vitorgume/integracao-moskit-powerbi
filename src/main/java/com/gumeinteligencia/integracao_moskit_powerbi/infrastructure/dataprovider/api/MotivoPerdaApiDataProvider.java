package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.api;

import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.api.MotivoPerdaGatewayApi;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.MotivoPerdaDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Component
@Slf4j
public class MotivoPerdaApiDataProvider implements MotivoPerdaGatewayApi {

    private final WebClient webClient;

    @Value("${moskit.api.key}")
    private final String apiKey;

    @Value("${moskit.api.base-url}")
    private final String baseUrl;

    public MotivoPerdaApiDataProvider(
            WebClient webClient,
            @Value("${moskit.api.key}") String apiKey,
            @Value("${moskit.api.base-url}") String baseUrl
    ){
        this.webClient = webClient;
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
    }

    @Override
    public Optional<MotivoPerdaDto> consultaMotivoPerda(Integer id) {
        log.info("Consultando motivo de perda na api...");
        String uri = baseUrl + "/lostReasons/" + id;

        Optional<MotivoPerdaDto> motivoPerda = webClient.get()
                .uri(uri)
                .header("apikey", apiKey)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Optional<MotivoPerdaDto>>() {})
                .block();

        log.info("Finalizado consulta de motivo de perda na api...");

        return motivoPerda;
    }
}
