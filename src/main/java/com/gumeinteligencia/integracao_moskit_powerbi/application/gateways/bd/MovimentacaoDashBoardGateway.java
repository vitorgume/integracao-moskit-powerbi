package com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.bd;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Movimentacao;

public interface MovimentacaoDashBoardGateway {
    void limpar();
    void salvar(Movimentacao movimentacao);
}
