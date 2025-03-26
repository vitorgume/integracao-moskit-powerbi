package com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.bd;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Atividade;

import java.util.List;

public interface AtividadeGateway {
    List<Atividade> listar();
    Atividade salvar(Atividade atividade);
}
