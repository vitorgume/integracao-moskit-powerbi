package com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.negocio;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Fase;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Funil;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Negocio;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Usuario;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.bd.NegocioDataProvider;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.FunilMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.MapperData;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.NegocioMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.service.FaseService;
import com.gumeinteligencia.integracao_moskit_powerbi.service.FunilService;
import com.gumeinteligencia.integracao_moskit_powerbi.service.UsuarioService;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.Atualiza;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.CampoPersonalizadoDto;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.NegocioDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AtualizaNegocioService implements Atualiza<NegocioDto> {

    @Value("${moskit.api.key}")
    private final String apiKey;

    @Value("${moskit.api.base-url}")
    private final String baseUrl;

    private final WebClient webClient;

    private final NegocioDataProvider dataProvider;

    private final UsuarioService usuarioService;

    private final FaseService faseService;

    private final FunilService funilService;

    public AtualizaNegocioService(
            @Value("${moskit.api.key}") String apiKey,
            @Value("${moskit.api.base-url}") String baseUrl,
            WebClient webClient,
            NegocioDataProvider dataProvider,
            UsuarioService usuarioService,
            FaseService faseService,
            FunilService funilService
    ) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
        this.webClient = webClient;
        this.dataProvider = dataProvider;
        this.usuarioService = usuarioService;
        this.faseService = faseService;
        this.funilService = funilService;
    }

    @Override
    public int atualiza() {
        System.out.println("Começando atualização de negócios...");
        List<NegocioDto> negociosDtos = consultaApi();

        List<Negocio> negociosNovos = negociosDtos.stream()
                .peek(negocio -> {
                    List<CampoPersonalizadoDto> camposPersonalizado = negocio.getEntityCustomFields()
                            .stream()
                            .filter(campoPersonalizado -> campoPersonalizado.id().equals("CF_8P5q4Vi6ioJ7lmRJ"))
                            .toList();

                    negocio.setEntityCustomFields(camposPersonalizado);
                })
                .toList()
                .stream()
                .map(NegocioMapper::paraDomainDeDto)
                .toList();

        AtomicInteger contAtualizacoes = new AtomicInteger();

        if (negociosNovos.isEmpty()) {
            throw new RuntimeException("Nenhum negócio encontrado");
        }

        List<Negocio> negociosAntigos = dataProvider.listarNegocios().stream().map(NegocioMapper::paraDomain).toList();

        List<Negocio> negociosCadastrar = negociosNovos.stream()
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
    public List<NegocioDto> consultaApi() {
        System.out.println("Consultando negócios na api...");
        String uri = baseUrl + "/deals";
        String nextPageToken = null;
        int quantity = 50;
        List<NegocioDto> todosNegocios = new ArrayList<>();

        do {
            String uriPaginada = nextPageToken == null
                    ? uri + "?quantity=" + quantity
                    : uri + "?quantity=" + quantity + "&nextPageToken=" + nextPageToken;

            var response = webClient.get()
                    .uri(uriPaginada)
                    .header("apikey", apiKey)
                    .retrieve()
                    .toEntityList(NegocioDto.class)
                    .block();



            if (response != null && response.getBody() != null) {
                List<NegocioDto> negociosFiltrados = new ArrayList<>(response.getBody()
                        .stream()
                        .filter(negocio -> MapperData
                                .trasnformaData(negocio.getDateCreated()).isAfter(LocalDate.of(2025, 01, 01))
                        )
                        .toList()
                );

                todosNegocios.addAll(new ArrayList<>(negociosFiltrados.stream().map(this::buscaFunil).toList()));
            }

            nextPageToken = response != null ? response.getHeaders().getFirst("X-Moskit-Listing-Next-Page-Token") : null;
        } while (nextPageToken != null && !nextPageToken.isEmpty());

        System.out.println("Finalizado consultas de negócios na api...");
        return todosNegocios;
    }

    private NegocioDto buscaFunil(NegocioDto negocio) {
        Fase fase = faseService.consultarPorId(negocio.getStage().getId());
        Funil funilStage = funilService.consultarPorId(fase.getPipeline().getId());
        negocio.getStage().setPipeline(FunilMapper.paraDto(funilStage));
        return negocio;
    }
}