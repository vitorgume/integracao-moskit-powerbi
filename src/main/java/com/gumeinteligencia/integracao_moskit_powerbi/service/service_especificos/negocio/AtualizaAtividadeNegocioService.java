package com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.negocio;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.*;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.MovimentacaoNegocioDataProvider;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.*;
import com.gumeinteligencia.integracao_moskit_powerbi.service.*;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.Atualiza;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AtualizaAtividadeNegocioService implements Atualiza<AtividadeNegocioDto> {

    @Value("${moskit.api.key}")
    private final String apiKey;

    @Value("${moskit.api.base-url}")
    private final String baseUrl;

    private final WebClient webClient;

    private final MovimentacaoNegocioDataProvider dataProvider;

    private final UsuarioService usuarioService;

    private final FaseService faseService;

    private final NegocioService negocioService;

    private final FunilService funilService;

    private final MovimentacaoNegocioService movimentacaoNegocioService;

    public AtualizaAtividadeNegocioService(
            @Value("${moskit.api.key}") String apiKey,
            @Value("${moskit.api.base-url}") String baseUrl,
            WebClient webClient,
            MovimentacaoNegocioDataProvider dataProvider,
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

    @Override
    public int atualiza() {
        System.out.println("Começando atualização de negócios...");
        List<AtividadeNegocio> todasAtividadeNegocio = consultaApi()
                .stream()
                .map(AtividadeNegocioMapper::paraDomainDeDto)
                .toList();

        List<AtividadeNegocio> atividadesAntigas;

        List<AtividadeNegocio> atividadesNovas = todasAtividadeNegocio.stream()
                .filter(empresaNova ->
                        empresasAntigas.stream().noneMatch(empresaAntiga ->
                                empresaAntiga.getName().equals(empresaNova.getName())
                        )
                )
                .toList();

        AtomicInteger contAtualizacoes = new AtomicInteger();

        if (atividadesNovas.isEmpty()) {
            throw new RuntimeException("Nenhum negócio encontrado");
        }

        List<Negocio> negociosAntigos = dataProvider.listarNegocios().stream().map(NegocioMapper::paraDomain).toList();

        List<Negocio> negociosCadastrar = atividadesNovas.stream()
                .filter(negocioNovo ->
                        negociosAntigos.stream().noneMatch(negocioAntigo ->
                                negocioAntigo.getName().equals(negocioNovo.getName())
                        )
                )
                .toList();

        negociosCadastrar.forEach(negocio -> {
            Usuario usuario = usuarioService.consultarPorId(negocio.getResponsible().getId());
            Fase fase = faseService.consultarPorId(negocio.getStage().getId());

            negocio.setResponsible(usuario);
            negocio.setCreatedBy(usuario);
            negocio.setStage(fase);

            Negocio negocioSalvo = NegocioMapper.paraDomain(dataProvider.salvar(NegocioMapper.paraEntity(negocio)));
            contAtualizacoes.getAndIncrement();
            System.out.println("Negócio salvo com sucesso: " + negocioSalvo);
        });

        System.out.println("Finalizado atualizações de negócios...");
        System.out.println("Quantidade de operações: " + contAtualizacoes.get());
        return contAtualizacoes.get();
    }

    @Override
    public List<AtividadeNegocioDto> consultaApi() {
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
