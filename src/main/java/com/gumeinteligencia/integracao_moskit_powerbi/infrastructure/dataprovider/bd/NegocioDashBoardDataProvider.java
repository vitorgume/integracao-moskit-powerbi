package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.bd;

import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.bd.NegocioDashBoardGateway;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Negocio;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.exceptions.DataProviderBancoDadosException;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.mapper.NegocioMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.NegocioDashBoardRepository;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.entities.NegocioDashBoardEntity;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.entities.NegocioEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NegocioDashBoardDataProvider implements NegocioDashBoardGateway {

    private final NegocioDashBoardRepository repository;
    private final String MENSAGEM_ERRO_LIMPAR = "Erro ao limpar tabela de negócio do dashboard.";
    private final String MENSAGEM_ERRO_SALVAR = "Erro ao salvar negócio dashboard.";

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
    public void salvar(Negocio negocio) {
        NegocioDashBoardEntity novoNegocio = NegocioMapper.paraEntityDashBoard(negocio);

        try {
            repository.save(novoNegocio);
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_SALVAR, ex);
            throw new DataProviderBancoDadosException(MENSAGEM_ERRO_SALVAR, ex.getCause());
        }
    }
}
