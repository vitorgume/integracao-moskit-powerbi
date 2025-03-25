package com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.api;

import com.gumeinteligencia.integracao_moskit_powerbi.application.service.dto.NegocioDto;

import java.util.List;

public interface NegocioGatewayApi {
    NegocioDto consultarPorId(Integer id);
    List<NegocioDto> consultarNegocios();
}
