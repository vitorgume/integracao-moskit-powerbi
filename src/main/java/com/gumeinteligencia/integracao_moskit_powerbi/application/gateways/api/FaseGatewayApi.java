package com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.api;

import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.FaseDto;

import java.util.List;

public interface FaseGatewayApi {
    List<FaseDto> consultaFases();
}
