package com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.bd;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Negocio;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.entities.NegocioEntity;

import java.util.List;
import java.util.Optional;

public interface NegocioGateway {
    List<Negocio> listar();
    Optional<Negocio> consultarPorId(Integer id);
    Negocio salvar(Negocio negocio);
}
