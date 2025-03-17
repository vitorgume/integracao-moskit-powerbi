package com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Fase;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Funil;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Negocio;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Usuario;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.NegocioDataProvider;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.FunilMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.NegocioMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.service.FaseService;
import com.gumeinteligencia.integracao_moskit_powerbi.service.FunilService;
import com.gumeinteligencia.integracao_moskit_powerbi.service.UsuarioService;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.NegocioDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AtualizaNegocioService implements Atualiza<NegocioDto>{

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
        List<Negocio> negociosNovos = consultaApi().stream().map(NegocioMapper::paraDomainDeDto).toList();
        AtomicInteger contAtualizacoes = new AtomicInteger();

        if(negociosNovos.isEmpty()) {
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


        return contAtualizacoes.get();
    }

    @Override
    public List<NegocioDto> consultaApi() {
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
                todosNegocios = new ArrayList<>(todosNegocios.stream().map(this::buscaFunil).toList());
                todosNegocios.addAll(response.getBody());
            }

            nextPageToken = response != null ? response.getHeaders().getFirst("X-Moskit-Listing-Next-Page-Token") : null;

        } while (nextPageToken != null && !nextPageToken.isEmpty());

        return todosNegocios;
    }

    private NegocioDto buscaFunil(NegocioDto negocio) {
        Fase fase = faseService.consultarPorId(negocio.stage().getId());
        Funil funilStage = funilService.consultarPorId(fase.getPipeline().getId());
        negocio.stage().setPipeline(FunilMapper.paraDto(funilStage));
        return negocio;
    }
}
