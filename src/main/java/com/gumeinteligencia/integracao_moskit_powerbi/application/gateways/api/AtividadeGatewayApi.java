package com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.api;

import com.gumeinteligencia.integracao_moskit_powerbi.application.service.dto.AtividadeNegocioDto;

import java.util.List;

public interface AtividadeGatewayApi {
    List<AtividadeNegocioDto> consultaAtividades();
}
