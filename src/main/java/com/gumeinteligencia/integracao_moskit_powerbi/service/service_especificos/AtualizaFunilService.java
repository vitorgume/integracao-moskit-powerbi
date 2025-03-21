package com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Funil;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.FunilDataProvider;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.FunilMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.FunilDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AtualizaFunilService implements Atualiza<FunilDto> {

    @Value("${moskit.api.key}")
    private final String apiKey;

    @Value("${moskit.api.base-url}")
    private final String baseUrl;

    private final WebClient webClient;

    private final FunilDataProvider dataProvider;

    public AtualizaFunilService(
            @Value("${moskit.api.key}") String apiKey,
            @Value("${moskit.api.base-url}") String baseUrl,
            WebClient webClient,
            FunilDataProvider dataProvider
    ) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
        this.webClient = webClient;
        this.dataProvider = dataProvider;
    }

    @Override
    public int atualiza() {
        System.out.println("Começando atualizações de funis...");
        List<Funil> funisNovos = consultaApi().stream().map(FunilMapper::paraDomainDeDto).toList();
        AtomicInteger contAtualizacoes = new AtomicInteger();

        if(funisNovos.isEmpty()) {
            throw new RuntimeException("Nenhum fúnil encontrado");
        }

        List<Funil> funisAntigos = dataProvider.listarFunis().stream().map(FunilMapper::paraDomain).toList();

        List<Funil> funisCadastrar = funisNovos.stream()
                .filter(funilNovo ->
                        funisAntigos.stream().noneMatch(funilAntigo ->
                                funilAntigo.getName().equals(funilNovo.getName())
                        )
                )
                .toList();

        funisCadastrar.forEach(funil -> {
            Funil funisSalvos = FunilMapper.paraDomain(dataProvider.salvar(FunilMapper.paraEntity(funil)));
            contAtualizacoes.getAndIncrement();
            System.out.println("Fúnil salvo com sucesso: " + funisSalvos.toString());
        });


        System.out.println("Finalizado atualizações de funis...");
        System.out.println("Quantidade de operações: " + contAtualizacoes.get());
        return contAtualizacoes.get();
    }

    @Override
    public List<FunilDto> consultaApi() {
        System.out.println("Consultando funis na api...");
        String uri = baseUrl + "/pipelines";

        List<FunilDto> funis = webClient.get()
                .uri(uri)
                .header("apikey", apiKey)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<FunilDto>>() {})
                .block();

        System.out.println("Finalizado consulta de funis da api...");
        return funis;
    }
}
