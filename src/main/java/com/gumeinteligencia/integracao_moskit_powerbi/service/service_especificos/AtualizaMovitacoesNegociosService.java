package com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Fase;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Funil;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.MovimentacoesNegocios;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Negocio;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.MovimentacoesNegociosDataProvider;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.FaseMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.FunilMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.MovimentacaoNegociosMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.UsuarioMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.service.*;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.FaseDto;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.MovimentacoesNegociosDto;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.NegocioDto;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.UsuarioDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AtualizaMovitacoesNegociosService {

    @Value("${moskit.api.key}")
    private final String apiKey;

    @Value("${moskit.api.base-url}")
    private final String baseUrl;

    private final WebClient webClient;

    private final MovimentacoesNegociosDataProvider dataProvider;

    private final UsuarioService usuarioService;

    private final FaseService faseService;

    private final NegocioService negocioService;

    private final FunilService funilService;

    private final MovimentacoesNegociosService movimentacoesNegociosService;

    public AtualizaMovitacoesNegociosService(
            @Value("${moskit.api.key}") String apiKey,
            @Value("${moskit.api.base-url}") String baseUrl,
            WebClient webClient,
            MovimentacoesNegociosDataProvider dataProvider,
            UsuarioService usuarioService,
            FaseService faseService,
            NegocioService negocioService,
            FunilService funilService,
            MovimentacoesNegociosService movimentacoesNegociosService
    ) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
        this.webClient = webClient;
        this.dataProvider = dataProvider;
        this.usuarioService = usuarioService;
        this.faseService = faseService;
        this.negocioService = negocioService;
        this.funilService = funilService;
        this.movimentacoesNegociosService = movimentacoesNegociosService;
    }

    public int atualiza() {
        System.out.println("Começando atualizações de movimentações...");

        List<Negocio> todosNegocios = negocioService.listar();
        List<MovimentacoesNegocios> movimentacoesNegociosList = movimentacoesNegociosService.listar();

        List<Negocio> negociosSemMovimentacao = todosNegocios.stream()
                .filter(negocio -> movimentacoesNegociosList.stream()
                        .noneMatch(movimentacao -> movimentacao.getNegocio().getId().equals(negocio.getId())))
                .toList();

        if(todosNegocios.isEmpty()) {
            throw new RuntimeException("Nenhum negócio encontrado.");
        }

        AtomicInteger contAtualizacoes = new AtomicInteger();

        negociosSemMovimentacao.forEach(negocio -> {
            List<MovimentacoesNegocios> movimentacoes = consultaApi(negocio.getId())
                    .stream()
                    .map(MovimentacaoNegociosMapper::paraDomainDeDto)
                    .toList();

            if (movimentacoes.isEmpty()) {
                throw new RuntimeException("Nenhuma movimentação encontrada");
            }

            List<MovimentacoesNegocios> movimentacoesAntigas = dataProvider.listar()
                    .stream()
                    .map(MovimentacaoNegociosMapper::paraDomain)
                    .toList();

            List<MovimentacoesNegocios> movimentacoesCadastrar = movimentacoes.stream()
                    .filter(movimentacaoNova ->
                            movimentacoesAntigas.stream().noneMatch(movimentacaoAntiga ->
                                    movimentacaoAntiga.getId().equals(movimentacaoNova.getId())
                            )
                    )
                    .toList();

            movimentacoesCadastrar.forEach(movimentacao -> {
                MovimentacoesNegocios movimentacaoSalva =  MovimentacaoNegociosMapper.paraDomain(dataProvider.salvar(MovimentacaoNegociosMapper.paraEntity(movimentacao)));
                contAtualizacoes.getAndIncrement();
                System.out.println("Movimentação salva com sucesso: " + movimentacaoSalva);
            });

        });

        System.out.println("Atualização de movimentações finalizada...");
        System.out.println("Quantidade de operações: " + contAtualizacoes.get());

        return contAtualizacoes.get();
    }

    public List<MovimentacoesNegociosDto> consultaApi(Integer idNegocio) {
        System.out.println("Consultando movimentações na API...");

        String uri = baseUrl + "/deals/" + idNegocio + "/movements";
        String nextPageToken = null;
        int quantity = 50;
        List<MovimentacoesNegociosDto> todasMovimentacoes = new ArrayList<>();

        do {
            String uriPaginada = nextPageToken == null
                    ? uri + "?quantity=" + quantity
                    : uri + "?quantity=" + quantity + "&nextPageToken=" + nextPageToken;

            var response = webClient.get()
                    .uri(uriPaginada)
                    .header("apikey", apiKey)
                    .retrieve()
                    .toEntityList(MovimentacoesNegociosDto.class)
                    .block();

            if (response != null && response.getBody() != null) {
                todasMovimentacoes.addAll(new ArrayList<>(response.getBody().stream().map(this::buscaDadosNecessarios).toList()));
            }

            nextPageToken = response != null ? response.getHeaders().getFirst("X-Moskit-Listing-Next-Page-Token") : null;
        } while (nextPageToken != null && !nextPageToken.isEmpty());

        System.out.println("Finalizado consultas de movimentações...");

        return todasMovimentacoes;
    }

    private MovimentacoesNegociosDto buscaDadosNecessarios(MovimentacoesNegociosDto movimentacao) {
        Fase faseAtual;

        if(movimentacao.getCurrentStage() == null) {
            faseAtual = negocioService.consultarPorId(movimentacao.getDeal().getId()).getStage();
            movimentacao.setCurrentStage(FaseMapper.paraDto(faseAtual));
        } else {
            faseAtual = faseService.consultarPorId(movimentacao.getCurrentStage().getId());
        }

        Funil funilStageAtual = funilService.consultarPorId(faseAtual.getPipeline().getId());
        movimentacao.getCurrentStage().setPipeline(FunilMapper.paraDto(funilStageAtual));

        if(movimentacao.getOldStage() != null) {
            Fase faseAntiga = faseService.consultarPorId(movimentacao.getOldStage().getId());
            Funil funilStageAntigo = funilService.consultarPorId(faseAntiga.getPipeline().getId());
            movimentacao.getOldStage().setPipeline(FunilMapper.paraDto(funilStageAntigo));
        }

        NegocioDto negocioDto = negocioService.consultaPorIdNaApi(movimentacao.getDeal().getId());

        Negocio negocio = negocioService.consultarPorId(movimentacao.getDeal().getId());
        FaseDto fase = FaseMapper.paraDto(faseService.consultarPorId(negocio.getStage().getId()));

        UsuarioDto usuarioResponsavel = UsuarioMapper.paraDto(usuarioService.consultarPorId(negocio.getResponsible().getId()));
        UsuarioDto usuarioCriador = UsuarioMapper.paraDto(usuarioService.consultarPorId(negocio.getCreatedBy().getId()));



        movimentacao.getDeal().setStage(fase);
        movimentacao.getDeal().setResponsible(usuarioResponsavel);
        movimentacao.getDeal().setCreatedBy(usuarioCriador);
        movimentacao.getDeal().setEntityCustomFields(negocioDto.getEntityCustomFields());

        return movimentacao;
    }
}
