package com.gumeinteligencia.integracao_moskit_powerbi.application.usecase;

import com.gumeinteligencia.integracao_moskit_powerbi.application.exceptions.NenhumNegocioEncontradoException;
import com.gumeinteligencia.integracao_moskit_powerbi.application.exceptions.NenhumaMovimentacaoEncontradaException;
import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.api.MovimentacaoGatewayApi;
import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.api.NegocioGatewayApi;
import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.bd.MovimentacaoGateway;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.FaseDto;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.MovimentacaoDto;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.NegocioDto;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.UsuarioDto;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Fase;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Funil;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Movimentacao;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Negocio;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.mapper.FaseMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.mapper.FunilMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.mapper.MovimentacaoMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.mapper.UsuarioMapper;
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
    private final FaseMapper faseMapper;
    private final FunilMapper funilMapper;
    private final UsuarioMapper usuarioMapper;
    private final MovimentacaoMapper movimentacaoMapper;

    public List<Movimentacao> listar() {
        log.info("Listando movimentações...");

        List<Movimentacao> movimentacaoList = gateway.listar();

        log.info("Listagem de movimentações conluida. Lista: {}", movimentacaoList);

        return movimentacaoList;
    }

    public int atualiza() {
        log.info("Começando atualizações de movimentações...");

        List<Negocio> todosNegocios = negocioUseCase.listar();


        List<Movimentacao> movimentacaoList = this.listar();

        List<Negocio> negociosSemMovimentacao = todosNegocios.stream()
                .filter(negocio -> movimentacaoList.stream()
                        .noneMatch(movimentacao -> movimentacao.getNegocio().getId().equals(negocio.getId())))
                .toList();

        if(todosNegocios.isEmpty()) {
            throw new NenhumNegocioEncontradoException();
        }

        AtomicInteger contAtualizacoes = new AtomicInteger();

        negociosSemMovimentacao.forEach(negocio -> {
            List<Movimentacao> movimentacoes = gatewayApi.consultaMovimentacoes(negocio.getId())
                    .stream()
                    .map(this::buscaDadosNecessarios)
                    .map(movimentacaoMapper::paraDomain)
                    .toList();

            if (movimentacoes.isEmpty()) {
                throw new NenhumaMovimentacaoEncontradaException();
            }

            List<Movimentacao> movimentacaosCorretas = movimentacoes
                    .stream()
                    .filter(movimentacao ->
                            !(movimentacao.getFaseAtual().getId() == 446947 &&
                            movimentacao.getFaseAntiga().getId() == 446689))
                    .toList();


            List<Movimentacao> movimentacoesCadastrar = movimentacaosCorretas.stream()
                    .filter(movimentacaoNova ->
                            movimentacaoList.stream().noneMatch(movimentacaoAntiga ->
                                    movimentacaoAntiga.getId().equals(movimentacaoNova.getId())
                            )
                    )
                    .toList();

            movimentacoesCadastrar.forEach(movimentacao -> {
                Movimentacao movimentacaoSalva =  this.salvar(movimentacao);
                contAtualizacoes.getAndIncrement();
                log.info("Movimentação atualizada com sucesso: {}", movimentacaoSalva);
            });

        });

        log.info("Atualização de movimentações finalizada...");
        log.info("Quantidade de operações: " + contAtualizacoes.get());

        return contAtualizacoes.get();
    }

    public Movimentacao salvar(Movimentacao novaMovimentacao) {
        log.info("Salvando movimentação. Movimentação: {}", novaMovimentacao);

        Movimentacao movimentacaoSalva = gateway.salvar(novaMovimentacao);

        log.info("Movimentação salva com sucesso. Movimentação: {}", movimentacaoSalva);

        return movimentacaoSalva;
    }

    private MovimentacaoDto buscaDadosNecessarios(MovimentacaoDto movimentacao) {
        Fase faseAtual;

        if(movimentacao.getCurrentStage() == null) {
            faseAtual = negocioUseCase.consultarPorId(movimentacao.getDeal().getId()).getStage();
            movimentacao.setCurrentStage(faseMapper.paraDto(faseAtual));
        } else {
            faseAtual = faseUseCase.consultarPorId(movimentacao.getCurrentStage().getId());
        }

        Funil funilStageAtual = funilUseCase.consultarPorId(faseAtual.getPipeline().getId());
        movimentacao.getCurrentStage().setPipeline(funilMapper.paraDto(funilStageAtual));

        if(movimentacao.getOldStage() != null) {
            Fase faseAntiga = faseUseCase.consultarPorId(movimentacao.getOldStage().getId());
            Funil funilStageAntigo = funilUseCase.consultarPorId(faseAntiga.getPipeline().getId());
            movimentacao.getOldStage().setPipeline(funilMapper.paraDto(funilStageAntigo));
        }

        NegocioDto negocioDto = negocioGatewayApi.consultarPorId(movimentacao.getDeal().getId());

        Negocio negocio = negocioUseCase.consultarPorId(movimentacao.getDeal().getId());
        FaseDto fase = faseMapper.paraDto(faseUseCase.consultarPorId(negocio.getStage().getId()));

        UsuarioDto usuarioResponsavel = usuarioMapper.paraDto(usuarioUseCase.consultarPorId(negocio.getResponsible().getId()));
        UsuarioDto usuarioCriador = usuarioMapper.paraDto(usuarioUseCase.consultarPorId(negocio.getCreatedBy().getId()));



        movimentacao.getDeal().setStage(fase);
        movimentacao.getDeal().setResponsible(usuarioResponsavel);
        movimentacao.getDeal().setCreatedBy(usuarioCriador);
        movimentacao.getDeal().setEntityCustomFields(negocioDto.getEntityCustomFields());

        return movimentacao;
    }
}
