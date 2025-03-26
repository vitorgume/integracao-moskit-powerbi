package com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.bd;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Movimentacao;

import java.util.List;

public interface MovimentacaoGateway {
    List<Movimentacao> listar();

    Movimentacao salvar(Movimentacao movimentacaoEntity);
}
