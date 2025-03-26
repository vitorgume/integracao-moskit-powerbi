package com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.api;

import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.UsuarioDto;

import java.util.List;

public interface UsuarioGatewayApi {
    List<UsuarioDto> consultarUsuarios();
}

