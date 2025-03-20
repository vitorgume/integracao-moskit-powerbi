package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider;

import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.entity.MovimentacaoNegocioEntity;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repository.MovimentacaoNegocioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class MovimentacaoNegocioDataProvider {

    private final MovimentacaoNegocioRepository repository;

    public List<MovimentacaoNegocioEntity> listar() {
        List<MovimentacaoNegocioEntity> movimentacoesNegociosEntities;

        try {
            movimentacoesNegociosEntities = repository.findAll();
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return movimentacoesNegociosEntities;
    }

    public MovimentacaoNegocioEntity salvar(MovimentacaoNegocioEntity novaMovimentacao) {
        MovimentacaoNegocioEntity movimentacaoNegocioEntity;

        try {
            movimentacaoNegocioEntity = repository.save(novaMovimentacao);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return movimentacaoNegocioEntity;
    }
}
