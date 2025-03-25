package com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.bd;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.AtividadeNegocio;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.entities.AtividadeEntity;

import java.util.List;

public interface AtividadeGateway {
    List<AtividadeNegocio> listar();
    AtividadeEntity salvar(AtividadeEntity atividadeEntity);
}
