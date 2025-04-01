package com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.api;

import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.NegocioDto;

import java.util.List;
import java.util.Optional;

public interface NegocioGatewayApi {
    NegocioDto consultarPorId(Integer id);
    List<NegocioDto> consultaNegocios();
    List<NegocioDto> consultarTodosNegocis();
}
