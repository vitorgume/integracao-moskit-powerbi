package com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.api;

import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.AtividadeDto;

import java.util.List;

public interface AtividadeGatewayApi {
    List<AtividadeDto> consultaAtividades();
}
