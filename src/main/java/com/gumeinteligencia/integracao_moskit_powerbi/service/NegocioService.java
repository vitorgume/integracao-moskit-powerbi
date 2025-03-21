package com.gumeinteligencia.integracao_moskit_powerbi.service;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Fase;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Funil;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Negocio;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Usuario;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.NegocioDataProvider;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.entity.NegocioEntity;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.FunilMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.NegocioMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.FunilDto;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.NegocioDto;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.negocio.AtualizaNegocioService;
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

    private final FaseService faseService;

    private final FunilService funilService;

    private final UsuarioService usuarioService;

    public NegocioService(
            @Value("${moskit.api.key}") String apiKey,
            @Value("${moskit.api.base-url}") String baseUrl,
            WebClient webClient,
            NegocioDataProvider dataProvider,
            FaseService faseService,
            FunilService funilService,
            UsuarioService usuarioService
    ) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
        this.webClient = webClient;
        this.dataProvider = dataProvider;
        this.faseService = faseService;
        this.funilService = funilService;
        this.usuarioService = usuarioService;
    }


    public List<Negocio> listar() {
        return dataProvider.listarNegocios().stream().map(NegocioMapper::paraDomain).toList();
    }

    public Negocio consultarPorId(Integer id) {
        Optional<NegocioEntity> negocioOptional = dataProvider.consultarPorId(id);

        if(negocioOptional.isEmpty()) {
            NegocioDto negocioApi = consultaPorIdNaApi(id);

            if(negocioApi == null){
                throw new RuntimeException("Negócio não encontrado com id: " + id);
            }

            Fase fase = faseService.consultarPorId(negocioApi.getStage().getId());
            FunilDto funilStage = FunilMapper.paraDto(funilService.consultarPorId(fase.getPipeline().getId()));
            negocioApi.getStage().setPipeline(funilStage);

            Negocio negocio = NegocioMapper.paraDomainDeDto(negocioApi);

            Usuario usuario = usuarioService.consultarPorId(negocioApi.getResponsible().id());

            negocio.setResponsible(usuario);
            negocio.setCreatedBy(usuario);
            negocio.setStage(fase);

            return NegocioMapper.paraDomain(dataProvider.salvar(NegocioMapper.paraEntity(negocio)));
        }

        return NegocioMapper.paraDomain(negocioOptional.get());
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
