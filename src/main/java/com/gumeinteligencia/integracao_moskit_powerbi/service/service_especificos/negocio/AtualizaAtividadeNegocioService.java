package com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.negocio;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.AtividadeNegocio;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.bd.AtividadeDataProvider;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AtualizaAtividadeNegocioService implements Atualiza<AtividadeNegocioDto> {


    private final WebClient webClient;

    private final AtividadeDataProvider dataProvider;

    private final UsuarioService usuarioService;

    private final NegocioService negocioService;

    private final EmpresaService empresaService;

    public AtualizaAtividadeNegocioService(
            @Value("${moskit.api.key}") String apiKey,
            @Value("${moskit.api.base-url}") String baseUrl,
            WebClient webClient,
            AtividadeDataProvider dataProvider,
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
