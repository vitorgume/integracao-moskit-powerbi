package com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.api;

import com.gumeinteligencia.integracao_moskit_powerbi.application.service.dto.MovimentacaoNegociosDto;

import java.util.List;

public interface MovimentacaoGatewayApi {
    List<MovimentacaoNegociosDto> consultaApi(Integer id);
}

