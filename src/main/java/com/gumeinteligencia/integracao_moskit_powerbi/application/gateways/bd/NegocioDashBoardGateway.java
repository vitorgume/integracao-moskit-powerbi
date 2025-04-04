package com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.bd;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Negocio;

public interface NegocioDashBoardGateway {
    void limpar();
    void salvar(Negocio negocio);
}
