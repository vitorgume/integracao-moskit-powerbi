package com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.bd;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Fase;

import java.util.List;
import java.util.Optional;

public interface FaseGateway {
    Optional<Fase> consultarPorId(Integer id);
    List<Fase> listar();
    Fase salvar(Fase fase);
}
