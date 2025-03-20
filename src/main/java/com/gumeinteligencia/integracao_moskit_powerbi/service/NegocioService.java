package com.gumeinteligencia.integracao_moskit_powerbi.service;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Negocio;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.MovimentacoesNegociosDataProvider;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.NegocioDataProvider;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.entity.NegocioEntity;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.NegocioMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.AtualizaNegocioService;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.MovimentacoesNegociosDto;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.NegocioDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
public class NegocioService {

    @Value("${moskit.api.key}")
    private final String apiKey;

    @Value("${moskit.api.base-url}")
    private final String baseUrl;

    private final NegocioDataProvider dataProvider;

    private final WebClient webClient;

    public NegocioService(
            @Value("${moskit.api.key}") String apiKey,
            @Value("${moskit.api.base-url}") String baseUrl,
            WebClient webClient,
            NegocioDataProvider dataProvider
    ) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
        this.webClient = webClient;
        this.dataProvider = dataProvider;
    }


    public List<Negocio> listar() {
        return dataProvider.listarNegocios().stream().map(NegocioMapper::paraDomain).toList();
    }

    public Negocio consultarPorId(Integer id) {
        Optional<NegocioEntity> negocio = dataProvider.consultarPorId(id);

        if(negocio.isEmpty()) {
            throw new RuntimeException("Negócio não encontrado com id: " + id);
        }

        return NegocioMapper.paraDomain(negocio.get());
    }

    public NegocioDto consultaPorIdNaApi(Integer id) {
        String uri = baseUrl + "/deals/" + id;

        var response = webClient.get()
                .uri(uri)
                .header("apikey", apiKey)
                .retrieve()
                .toEntityList(NegocioDto.class)
                .block();

        if(response == null) {
            throw new RuntimeException("Negócio não encontrado na API.");
        }

        return response.getBody().get(0);
    }
}
