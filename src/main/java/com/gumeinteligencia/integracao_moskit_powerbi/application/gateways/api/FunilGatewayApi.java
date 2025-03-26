package com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.api;

import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.FunilDto;

import java.util.List;

public interface FunilGatewayApi {
    List<FunilDto> consultarFunis();
}

