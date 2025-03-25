package com.gumeinteligencia.integracao_moskit_powerbi.application.service;

import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.api.AtividadeGatewayApi;
import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.bd.AtividadeGateway;
import com.gumeinteligencia.integracao_moskit_powerbi.application.service.dto.AtividadeNegocioDto;
import com.gumeinteligencia.integracao_moskit_powerbi.application.service.dto.EmpresaDto;
import com.gumeinteligencia.integracao_moskit_powerbi.application.service.dto.NegocioDto;
import com.gumeinteligencia.integracao_moskit_powerbi.application.service.dto.UsuarioDto;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.AtividadeNegocio;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.AtividadeNegocioMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.EmpresaMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.NegocioMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
@RequiredArgsConstructor
public class AtividadeUseCase {

    private final AtividadeGateway gateway;
    private final AtividadeGatewayApi gatewayApi;
    private final UsuarioUseCase usuarioUseCase;
    private final NegocioUseCase negocioUseCase;
    private final EmpresaUseCase empresaUseCase;

    public int atualiza() {
        System.out.println("Começando atualização de atividades...");
        List<AtividadeNegocio> todasAtividadeNegocio = gatewayApi.consultaAtividades()
                .stream()
                .map(AtividadeNegocioMapper::paraDomainDeDto)
                .toList();

        List<AtividadeNegocio> atividadesAntigas = gateway.listar();

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


            AtividadeNegocio atividadeSalva = AtividadeNegocioMapper.paraDomain(gateway.salvar(AtividadeNegocioMapper.paraEntity(atividade)));
            contAtualizacoes.getAndIncrement();
            System.out.println("Atividade salva com sucesso: " + atividadeSalva);
        });

        System.out.println("Finalizado atualizações de atividades...");
        System.out.println("Quantidade de operações: " + contAtualizacoes.get());
        return contAtualizacoes.get();
    }

    private AtividadeNegocioDto buscaDadosNecessarios(AtividadeNegocioDto atividade) {

        UsuarioDto usuarioResponsible = UsuarioMapper.paraDto(
                usuarioUseCase.consultarPorId(atividade.getResponsible().id())
        );

        UsuarioDto usuarioCreated = UsuarioMapper.paraDto(
                usuarioUseCase.consultarPorId(atividade.getCreatedBy().id())
        );

        UsuarioDto usuarioDone = UsuarioMapper.paraDto(
                usuarioUseCase.consultarPorId(atividade.getDoneUser().id())
        );

        List<NegocioDto> negocios = atividade.getDeals();

        if (!negocios.isEmpty()) {
            negocios = negocios.stream()
                    .map(negocio -> NegocioMapper.paraDto(
                                    negocioUseCase.consultarPorId(negocio.getId())
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
                                    empresaUseCase.consultarPorId(empresa.id())
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
