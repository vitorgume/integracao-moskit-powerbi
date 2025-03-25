package com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.api;

import com.gumeinteligencia.integracao_moskit_powerbi.application.service.dto.EmpresaDto;

import java.util.List;

public interface EmpresaGatewayApi {
    List<EmpresaDto> consultaEmpresas();
}
