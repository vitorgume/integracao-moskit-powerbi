package com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.bd;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Empresa;

import java.util.List;
import java.util.Optional;

public interface EmpresaGateway {
    Optional<Empresa> consultarPorId(Integer id);
    List<Empresa> listar();
    Empresa salvar(Empresa empresa);
}
