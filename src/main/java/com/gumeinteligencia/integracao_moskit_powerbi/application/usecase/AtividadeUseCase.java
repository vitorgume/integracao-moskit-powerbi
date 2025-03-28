package com.gumeinteligencia.integracao_moskit_powerbi.application.usecase;

import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.api.AtividadeGatewayApi;
import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.bd.AtividadeGateway;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.AtividadeDto;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.EmpresaDto;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.NegocioDto;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.UsuarioDto;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.mapper.EmpresaMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.mapper.NegocioMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AtividadeUseCase {

    private final AtividadeGateway gateway;
    private final AtividadeGatewayApi gatewayApi;
    private final UsuarioUseCase usuarioUseCase;
    private final NegocioUseCase negocioUseCase;
    private final EmpresaUseCase empresaUseCase;
    private final NegocioMapper negocioMapper;
    private final UsuarioMapper usuarioMapper;
    private final EmpresaMapper empresaMapper;

    public int atualiza() {
//        System.out.println("Começando atualização de atividades...");
//        List<AtividadeNegocio> todasAtividadeNegocio = gatewayApi.consultaAtividades()
//                .stream()
//                .map(AtividadeNegocioMapper::paraDomainDeDto)
//                .toList();
//
//        List<AtividadeNegocio> atividadesAntigas = gateway.listar();
//
//        List<AtividadeNegocio> atividadesNovas = todasAtividadeNegocio.stream()
//                .filter(atividade ->
//                        atividadesAntigas.stream().noneMatch(atividadeAntiga ->
//                                atividadeAntiga.getId().equals(atividade.getId())
//                        )
//                )
//                .toList();
//
//        AtomicInteger contAtualizacoes = new AtomicInteger();
//
////        if (atividadesNovas.isEmpty()) {
////            throw new RuntimeException("Nenhuma atividade encontrada.");
////        }
//
//
//        atividadesNovas.forEach(atividade -> {
//
//
//            AtividadeNegocio atividadeSalva = gateway.salvar(atividade);
//            contAtualizacoes.getAndIncrement();
//            System.out.println("Atividade salva com sucesso: " + atividadeSalva);
//        });
//
//        System.out.println("Finalizado atualizações de atividades...");
//        System.out.println("Quantidade de operações: " + contAtualizacoes.get());
        return 0;
    }

    private AtividadeDto buscaDadosNecessarios(AtividadeDto atividade) {

        UsuarioDto usuarioResponsible = usuarioMapper.paraDto(
                usuarioUseCase.consultarPorId(atividade.getResponsible().id())
        );

        UsuarioDto usuarioCreated = usuarioMapper.paraDto(
                usuarioUseCase.consultarPorId(atividade.getCreatedBy().id())
        );

        UsuarioDto usuarioDone = usuarioMapper.paraDto(
                usuarioUseCase.consultarPorId(atividade.getDoneUser().id())
        );

        List<NegocioDto> negocios = atividade.getDeals();

        if (!negocios.isEmpty()) {
            negocios = negocios.stream()
                    .map(negocio -> negocioMapper.paraDto(
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
                    .map(empresa -> empresaMapper.paraDto(
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
