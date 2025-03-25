package com.gumeinteligencia.integracao_moskit_powerbi.application.service;

import com.gumeinteligencia.integracao_moskit_powerbi.application.exceptions.NenhumNegocioEncontradoException;
import com.gumeinteligencia.integracao_moskit_powerbi.application.exceptions.NenhumaMovimentacaoEncontradaException;
import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.api.MovimentacaoGatewayApi;
import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.api.NegocioGatewayApi;
import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.bd.MovimentacaoGateway;
import com.gumeinteligencia.integracao_moskit_powerbi.application.service.dto.FaseDto;
import com.gumeinteligencia.integracao_moskit_powerbi.application.service.dto.MovimentacaoNegociosDto;
import com.gumeinteligencia.integracao_moskit_powerbi.application.service.dto.NegocioDto;
import com.gumeinteligencia.integracao_moskit_powerbi.application.service.dto.UsuarioDto;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Fase;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Funil;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.MovimentacaoNegocio;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Negocio;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.FaseMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.FunilMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.MovimentacaoNegocioMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovimentacaoUseCase {

    private final MovimentacaoGateway gateway;
    private final MovimentacaoGatewayApi gatewayApi;
    private final NegocioGatewayApi negocioGatewayApi;
    private final NegocioUseCase negocioUseCase;
    private final FaseUseCase faseUseCase;
    private final FunilUseCase funilUseCase;
    private final UsuarioUseCase usuarioUseCase;

    public List<MovimentacaoNegocio> listar() {
        log.info("Listando movimentações...");

        List<MovimentacaoNegocio> movimentacaoNegocioList = gateway.listar();

        log.info("Listagem de movimentações conluida. Lista: {}", movimentacaoNegocioList);

        return movimentacaoNegocioList;
    }

    public int atualiza() {
        log.info("Começando atualizações de movimentações...");

        List<Negocio> todosNegocios = negocioUseCase.listar();


        List<MovimentacaoNegocio> movimentacaoNegocioList = this.listar();

        List<Negocio> negociosSemMovimentacao = todosNegocios.stream()
                .filter(negocio -> movimentacaoNegocioList.stream()
                        .noneMatch(movimentacao -> movimentacao.getNegocio().getId().equals(negocio.getId())))
                .toList();

        if(todosNegocios.isEmpty()) {
            throw new NenhumNegocioEncontradoException();
        }

        AtomicInteger contAtualizacoes = new AtomicInteger();

        negociosSemMovimentacao.forEach(negocio -> {
            List<MovimentacaoNegocio> movimentacoes = gatewayApi.consultaApi(negocio.getId())
                    .stream()
                    .map(this::buscaDadosNecessarios)
                    .map(MovimentacaoNegocioMapper::paraDomainDeDto)
                    .toList();

            if (movimentacoes.isEmpty()) {
                throw new NenhumaMovimentacaoEncontradaException();
            }


            List<MovimentacaoNegocio> movimentacoesCadastrar = movimentacoes.stream()
                    .filter(movimentacaoNova ->
                            movimentacaoNegocioList.stream().noneMatch(movimentacaoAntiga ->
                                    movimentacaoAntiga.getId().equals(movimentacaoNova.getId())
                            )
                    )
                    .toList();

            movimentacoesCadastrar.forEach(movimentacao -> {
                MovimentacaoNegocio movimentacaoSalva =  this.salvar(movimentacao);
                contAtualizacoes.getAndIncrement();
                log.info("Movimentação atualizada com sucesso: {}", movimentacaoSalva);
            });

        });

        log.info("Atualização de movimentações finalizada...");
        log.info("Quantidade de operações: " + contAtualizacoes.get());

        return contAtualizacoes.get();
    }

    public MovimentacaoNegocio salvar(MovimentacaoNegocio novaMovimentacao) {
        log.info("Salvando movimentação. Movimentação: {}", novaMovimentacao);

        MovimentacaoNegocio movimentacaoSalva = gateway.salvar(novaMovimentacao);

        log.info("Movimentação salva com sucesso. Movimentação: {}", movimentacaoSalva);

        return movimentacaoSalva;
    }

    private MovimentacaoNegociosDto buscaDadosNecessarios(MovimentacaoNegociosDto movimentacao) {
        Fase faseAtual;

        if(movimentacao.getCurrentStage() == null) {
            faseAtual = negocioUseCase.consultarPorId(movimentacao.getDeal().getId()).getStage();
            movimentacao.setCurrentStage(FaseMapper.paraDto(faseAtual));
        } else {
            faseAtual = faseUseCase.consultarPorId(movimentacao.getCurrentStage().getId());
        }

        Funil funilStageAtual = funilUseCase.consultarPorId(faseAtual.getPipeline().getId());
        movimentacao.getCurrentStage().setPipeline(FunilMapper.paraDto(funilStageAtual));

        if(movimentacao.getOldStage() != null) {
            Fase faseAntiga = faseUseCase.consultarPorId(movimentacao.getOldStage().getId());
            Funil funilStageAntigo = funilUseCase.consultarPorId(faseAntiga.getPipeline().getId());
            movimentacao.getOldStage().setPipeline(FunilMapper.paraDto(funilStageAntigo));
        }

        NegocioDto negocioDto = negocioGatewayApi.consultarPorId(movimentacao.getDeal().getId());

        Negocio negocio = negocioUseCase.consultarPorId(movimentacao.getDeal().getId());
        FaseDto fase = FaseMapper.paraDto(faseUseCase.consultarPorId(negocio.getStage().getId()));

        UsuarioDto usuarioResponsavel = UsuarioMapper.paraDto(usuarioUseCase.consultarPorId(negocio.getResponsible().getId()));
        UsuarioDto usuarioCriador = UsuarioMapper.paraDto(usuarioUseCase.consultarPorId(negocio.getCreatedBy().getId()));



        movimentacao.getDeal().setStage(fase);
        movimentacao.getDeal().setResponsible(usuarioResponsavel);
        movimentacao.getDeal().setCreatedBy(usuarioCriador);
        movimentacao.getDeal().setEntityCustomFields(negocioDto.getEntityCustomFields());

        return movimentacao;
    }
}
