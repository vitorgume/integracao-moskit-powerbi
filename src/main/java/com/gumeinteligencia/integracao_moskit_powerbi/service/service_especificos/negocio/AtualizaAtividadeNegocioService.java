package com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.negocio;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.AtividadeNegocio;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.AtividadeNegocioDataProvider;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.*;
import com.gumeinteligencia.integracao_moskit_powerbi.service.*;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.Atualiza;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.AtividadeNegocioDto;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.EmpresaDto;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.NegocioDto;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.UsuarioDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AtualizaAtividadeNegocioService implements Atualiza<AtividadeNegocioDto> {

    @Value("${moskit.api.key}")
    private final String apiKey;

    @Value("${moskit.api.base-url}")
    private final String baseUrl;

    private final WebClient webClient;

    private final AtividadeNegocioDataProvider dataProvider;

    private final UsuarioService usuarioService;

    private final NegocioService negocioService;

    private final EmpresaService empresaService;

    public AtualizaAtividadeNegocioService(
            @Value("${moskit.api.key}") String apiKey,
            @Value("${moskit.api.base-url}") String baseUrl,
            WebClient webClient,
            AtividadeNegocioDataProvider dataProvider,
            UsuarioService usuarioService,
            FaseService faseService,
            NegocioService negocioService,
            FunilService funilService,
            EmpresaService empresaService
    ) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
        this.webClient = webClient;
        this.dataProvider = dataProvider;
        this.usuarioService = usuarioService;
        this.negocioService = negocioService;
        this.empresaService = empresaService;
    }

    @Override
    public int atualiza() {
        System.out.println("Começando atualização de atividades...");
        List<AtividadeNegocio> todasAtividadeNegocio = consultaApi()
                .stream()
                .map(AtividadeNegocioMapper::paraDomainDeDto)
                .toList();

        List<AtividadeNegocio> atividadesAntigas = dataProvider.listar().stream().map(AtividadeNegocioMapper::paraDomain).toList();

        List<AtividadeNegocio> atividadesNovas = todasAtividadeNegocio.stream()
                .filter(atividade ->
                        atividadesAntigas.stream().noneMatch(atividadeAntiga ->
                                atividadeAntiga.getId().equals(atividade.getId())
                        )
                )
                .toList();

        AtomicInteger contAtualizacoes = new AtomicInteger();

//        if (atividadesNovas.isEmpty()) {
//            throw new RuntimeException("Nenhuma atividade encontrada.");
//        }


        atividadesNovas.forEach(atividade -> {


            AtividadeNegocio atividadeSalva = AtividadeNegocioMapper.paraDomain(dataProvider.salvar(AtividadeNegocioMapper.paraEntity(atividade)));
            contAtualizacoes.getAndIncrement();
            System.out.println("Atividade salva com sucesso: " + atividadeSalva);
        });

        System.out.println("Finalizado atualizações de atividades...");
        System.out.println("Quantidade de operações: " + contAtualizacoes.get());
        return contAtualizacoes.get();
    }

    @Override
    public List<AtividadeNegocioDto> consultaApi() {
        System.out.println("Consultando atividades na API...");

        String uri = baseUrl + "/activities/search";

        List<Map<String, Object>> requestBody = List.of(
                Map.of(
                        "field", "dateCreated",
                        "expression", "gte",
                        "values", List.of("2025-01-01T00:00:00Z")
                )
        );

        var response = webClient.post()
                .uri(uri) //
                .header("apikey", apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody) // Enviando o corpo JSON
                .retrieve()
                .toEntityList(AtividadeNegocioDto.class)
                .block();

        if(response == null || response.getBody() == null) {
            throw new RuntimeException("Nenhuma atividad encontrada.");
        }

        List<AtividadeNegocioDto> todasAtividades = new ArrayList<>(new ArrayList<>(response.getBody().stream().map(this::buscaDadosNecessarios).toList()));

        System.out.println("Finalizado consultas de atividades...");

        return todasAtividades;
    }

    private AtividadeNegocioDto buscaDadosNecessarios(AtividadeNegocioDto atividade) {

        UsuarioDto usuarioResponsible = UsuarioMapper.paraDto(
                usuarioService.consultarPorId(atividade.getResponsible().id())
        );

        UsuarioDto usuarioCreated = UsuarioMapper.paraDto(
                usuarioService.consultarPorId(atividade.getCreatedBy().id())
        );

        UsuarioDto usuarioDone = UsuarioMapper.paraDto(
                usuarioService.consultarPorId(atividade.getDoneUser().id())
        );

        List<NegocioDto> negocios = atividade.getDeals();

        if (!negocios.isEmpty()) {
            negocios = negocios.stream()
                    .map(negocio -> NegocioMapper.paraDto(
                                    negocioService.consultarPorId(negocio.getId())
                            )
                    )
                    .toList();
        } else {
            negocios = new ArrayList<>();
        }

        List<EmpresaDto> empresas = atividade.getCompanies();

        if (!empresas.isEmpty()) {
            empresas = empresas.stream()
                    .map(empresa -> EmpresaMapper.paraDto(
                                    empresaService.consultarPorId(empresa.id())
                            )
                    )
                    .toList();
        } else {
            empresas = new ArrayList<>();
        }


        atividade.setResponsible(usuarioResponsible);
        atividade.setCreatedBy(usuarioCreated);
        atividade.setDoneUser(usuarioDone);

        atividade.setDeals(negocios);
        atividade.setCompanies(empresas);

        return atividade;
    }
}
