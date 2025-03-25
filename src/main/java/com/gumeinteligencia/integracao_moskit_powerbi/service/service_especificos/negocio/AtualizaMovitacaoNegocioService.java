package com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.negocio;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Fase;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Funil;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.MovimentacaoNegocio;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Negocio;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.bd.MovimentacaoDataProvider;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.FaseMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.FunilMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.MovimentacaoNegocioMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.UsuarioMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.service.*;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.FaseDto;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.MovimentacaoNegociosDto;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.NegocioDto;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.UsuarioDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AtualizaMovitacaoNegocioService {

    @Value("${moskit.api.key}")
    private final String apiKey;

    @Value("${moskit.api.base-url}")
    private final String baseUrl;

    private final WebClient webClient;

    private final MovimentacaoDataProvider dataProvider;

    private final UsuarioService usuarioService;

    private final FaseService faseService;

    private final NegocioService negocioService;

    private final FunilService funilService;

    private final MovimentacaoNegocioService movimentacaoNegocioService;

    public AtualizaMovitacaoNegocioService(
            @Value("${moskit.api.key}") String apiKey,
            @Value("${moskit.api.base-url}") String baseUrl,
            WebClient webClient,
            MovimentacaoDataProvider dataProvider,
            UsuarioService usuarioService,
            FaseService faseService,
            NegocioService negocioService,
            FunilService funilService,
            MovimentacaoNegocioService movimentacaoNegocioService
    ) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
        this.webClient = webClient;
        this.dataProvider = dataProvider;
        this.usuarioService = usuarioService;
        this.faseService = faseService;
        this.negocioService = negocioService;
        this.funilService = funilService;
        this.movimentacaoNegocioService = movimentacaoNegocioService;
    }

    public int atualiza() {
        System.out.println("Começando atualizações de movimentações...");

        List<Negocio> todosNegocios = negocioService.listar();
        List<MovimentacaoNegocio> movimentacaoNegocioList = movimentacaoNegocioService.listar();

        List<Negocio> negociosSemMovimentacao = todosNegocios.stream()
                .filter(negocio -> movimentacaoNegocioList.stream()
                        .noneMatch(movimentacao -> movimentacao.getNegocio().getId().equals(negocio.getId())))
                .toList();

        if(todosNegocios.isEmpty()) {
            throw new RuntimeException("Nenhum negócio encontrado.");
        }

        AtomicInteger contAtualizacoes = new AtomicInteger();

        negociosSemMovimentacao.forEach(negocio -> {
            List<MovimentacaoNegocio> movimentacoes = consultaApi(negocio.getId())
                    .stream()
                    .map(MovimentacaoNegocioMapper::paraDomainDeDto)
                    .toList();

            if (movimentacoes.isEmpty()) {
                throw new RuntimeException("Nenhuma movimentação encontrada");
            }

            List<MovimentacaoNegocio> movimentacoesAntigas = dataProvider.listar()
                    .stream()
                    .map(MovimentacaoNegocioMapper::paraDomain)
                    .toList();

            List<MovimentacaoNegocio> movimentacoesCadastrar = movimentacoes.stream()
                    .filter(movimentacaoNova ->
                            movimentacoesAntigas.stream().noneMatch(movimentacaoAntiga ->
                                    movimentacaoAntiga.getId().equals(movimentacaoNova.getId())
                            )
                    )
                    .toList();

            movimentacoesCadastrar.forEach(movimentacao -> {
                MovimentacaoNegocio movimentacaoSalva =  MovimentacaoNegocioMapper.paraDomain(dataProvider.salvar(MovimentacaoNegocioMapper.paraEntity(movimentacao)));
                contAtualizacoes.getAndIncrement();
                System.out.println("Movimentação salva com sucesso: " + movimentacaoSalva);
            });

        });

        System.out.println("Atualização de movimentações finalizada...");
        System.out.println("Quantidade de operações: " + contAtualizacoes.get());

        return contAtualizacoes.get();
    }

    public List<MovimentacaoNegociosDto> consultaApi(Integer idNegocio) {
        System.out.println("Consultando movimentações na API...");

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
                todasMovimentacoes.addAll(new ArrayList<>(response.getBody().stream().map(this::buscaDadosNecessarios).toList()));
            }

            nextPageToken = response != null ? response.getHeaders().getFirst("X-Moskit-Listing-Next-Page-Token") : null;
        } while (nextPageToken != null && !nextPageToken.isEmpty());

        System.out.println("Finalizado consultas de movimentações...");

        return todasMovimentacoes;
    }

    private MovimentacaoNegociosDto buscaDadosNecessarios(MovimentacaoNegociosDto movimentacao) {
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
