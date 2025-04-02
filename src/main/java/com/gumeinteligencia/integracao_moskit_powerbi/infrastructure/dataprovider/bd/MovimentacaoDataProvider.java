package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.bd;

import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.bd.MovimentacaoGateway;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Movimentacao;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.exceptions.DataProviderBancoDadosException;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.mapper.MovimentacaoMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.entities.MovimentacaoEntity;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.MovimentacaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class MovimentacaoDataProvider implements MovimentacaoGateway {

    private final MovimentacaoRepository repository;
    private final String MENSAGEM_ERRO_LISTAR_MOVIMENTACOES = "Erro ao listar movimentações.";
    private final String MENSAGEM_ERRO_SALVAR_MOVIMENTACAO = "Erro ao salvar movimentação";

    @Override
    public List<Movimentacao> listar() {
        List<MovimentacaoEntity> movimentacoesNegociosEntities;

        try {
            movimentacoesNegociosEntities = repository.findAll();
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_LISTAR_MOVIMENTACOES, ex);
            throw new DataProviderBancoDadosException(MENSAGEM_ERRO_LISTAR_MOVIMENTACOES, ex.getCause());
        }

        return movimentacoesNegociosEntities.stream().map(MovimentacaoMapper::paraDomain).toList();
    }

    @Override
    public Movimentacao salvar(Movimentacao movimentacao) {
        MovimentacaoEntity movimentacaoEntity;

        try {
            movimentacaoEntity = repository.save(MovimentacaoMapper.paraEntity(movimentacao));
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_SALVAR_MOVIMENTACAO, ex);
            throw new RuntimeException(MENSAGEM_ERRO_SALVAR_MOVIMENTACAO, ex.getCause());
        }

        return MovimentacaoMapper.paraDomain(movimentacaoEntity);
    }
}
