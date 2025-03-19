package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider;

import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.entity.MovimentacoesNegociosEntity;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repository.MovimentacoesNegociosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class MovimentacoesNegociosDataProvider {

    private final MovimentacoesNegociosRepository repository;

    public List<MovimentacoesNegociosEntity> listar() {
        List<MovimentacoesNegociosEntity> movimentacoesNegociosEntities;

        try {
            movimentacoesNegociosEntities = repository.findAll();
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return movimentacoesNegociosEntities;
    }

    public MovimentacoesNegociosEntity salvar(MovimentacoesNegociosEntity novaMovimentacao) {
        MovimentacoesNegociosEntity movimentacoesNegociosEntity;

        try {
            movimentacoesNegociosEntity = repository.save(novaMovimentacao);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return movimentacoesNegociosEntity;
    }
}
