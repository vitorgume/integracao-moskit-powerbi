package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.bd;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.MovimentacaoNegocio;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.exception.DataProviderException;
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
public class MovimentacaoDataProvider {

    private final MovimentacaoRepository repository;
    private final String MENSAGEM_ERRO_LISTAR_MOVIMENTACOES = "Erro ao listar movimentações.";
    private final String MENSAGEM_ERRO_SALVAR_MOVIMENTACAO = "Erro ao salvar movimentação";

    public List<MovimentacaoNegocio> listar() {
        List<MovimentacaoEntity> movimentacoesNegociosEntities;

        try {
            movimentacoesNegociosEntities = repository.findAll();
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_LISTAR_MOVIMENTACOES, ex);
            throw new DataProviderException(MENSAGEM_ERRO_LISTAR_MOVIMENTACOES, ex.getCause());
        }

        return movimentacoesNegociosEntities.stream().map(MovimentacaoMapper::paraDomain).toList();
    }

    public MovimentacaoNegocio salvar(MovimentacaoNegocio movimentacao) {
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
