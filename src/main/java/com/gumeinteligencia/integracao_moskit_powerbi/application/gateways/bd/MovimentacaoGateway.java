package com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.bd;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.MovimentacaoNegocio;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.entities.MovimentacaoEntity;

import java.util.List;

public interface MovimentacaoGateway {
    List<MovimentacaoNegocio> listar();

    MovimentacaoNegocio salvar(MovimentacaoNegocio movimentacaoEntity);
}
