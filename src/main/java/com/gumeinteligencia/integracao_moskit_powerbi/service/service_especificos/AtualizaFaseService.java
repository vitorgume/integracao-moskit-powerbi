package com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Fase;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Funil;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.FaseDataProvider;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.FaseMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.service.FunilService;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.FaseDto;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.NegocioDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AtualizaFaseService implements Atualiza<FaseDto>{

    @Value("${moskit.api.key}")
    private final String apiKey;

    @Value("${moskit.api.base-url}")
    private final String baseUrl;

    private final WebClient webClient;

    private final FaseDataProvider dataProvider;

    private final FunilService funilService;

    public AtualizaFaseService(
            @Value("${moskit.api.key}") String apiKey,
            @Value("${moskit.api.base-url}") String baseUrl,
            WebClient webClient,
            FaseDataProvider dataProvider,
            FunilService funilService
    ) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
        this.webClient = webClient;
        this.dataProvider = dataProvider;
        this.funilService = funilService;
    }


    @Override
    public int atualiza() {
        List<Fase> faseNovas = consultaApi().stream().map(FaseMapper::paraDomainDeDto).toList();
        AtomicInteger contAtualizacoes = new AtomicInteger();

        if(faseNovas.isEmpty()) {
            throw new RuntimeException("Nenhuma fase encontrado");
        }

        List<Fase> fasesAntigas = dataProvider.listarFases().stream().map(FaseMapper::paraDomain).toList();

        List<Fase> fasesCadastrar = faseNovas.stream()
                .filter(faseNovo ->
                        fasesAntigas.stream().noneMatch(faseAntiga ->
                                faseAntiga.getName().equals(faseNovo.getName())
                        )
                )
                .toList();

        fasesCadastrar.forEach(fase -> {
            Funil funil = funilService.consultarPorId(fase.getPipeline().getId());
            fase.setPipeline(funil);
            Fase fasesSalvas = FaseMapper.paraDomain(dataProvider.salvar(FaseMapper.paraEntity(fase)));
            contAtualizacoes.getAndIncrement();
            System.out.println("Fase salva com sucesso: " + fasesSalvas.toString());
        });


        return contAtualizacoes.get();
    }

    @Override
    public List<FaseDto> consultaApi() {
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

        return todasFases;
    }
}
