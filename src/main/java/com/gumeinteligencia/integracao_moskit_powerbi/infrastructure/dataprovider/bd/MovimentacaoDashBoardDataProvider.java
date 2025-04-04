package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.bd;

import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.bd.MovimentacaoDashBoardGateway;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Movimentacao;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.exceptions.DataProviderBancoDadosException;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.mapper.MovimentacaoMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.MovimentacaoDashBoardRepository;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.entities.MovimentacaoDashBoardEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MovimentacaoDashBoardDataProvider implements MovimentacaoDashBoardGateway {

    private final MovimentacaoDashBoardRepository repository;
    private final String MENSAGEM_ERRO_LIMPAR = "Erro ao limpar tabela de movimentações do dashboard.";
    private final String MENSAGEM_ERRO_SALVAR = "Erro ao salvar movimentação dashboard.";

    @Override
    public void limpar() {
        try {
            repository.deleteAll();
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_LIMPAR, ex);
            throw new DataProviderBancoDadosException(MENSAGEM_ERRO_LIMPAR, ex.getCause());
        }
    }

    @Override
    public void salvar(Movimentacao movimentacao) {
        MovimentacaoDashBoardEntity movimentacaoDashBoardEntity = MovimentacaoMapper.paraEntityDashBoard(movimentacao);

        try {
            repository.save(movimentacaoDashBoardEntity);
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_SALVAR, ex);
            throw new DataProviderBancoDadosException(MENSAGEM_ERRO_SALVAR, ex.getCause());
        }
    }
}
