package com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.bd;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Funil;

import java.util.List;
import java.util.Optional;

public interface FunilGateway {
    Optional<Funil> consultarPorId(Integer id);
    List<Funil> listar();
    Funil salvar(Funil novoFunil);
}
