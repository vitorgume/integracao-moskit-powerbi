package com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.api;

import com.gumeinteligencia.integracao_moskit_powerbi.application.service.dto.UsuarioDto;

import java.util.Optional;

public interface UsuarioGatewayApi {
    Optional<UsuarioDto> consultarUsuarios();
}

