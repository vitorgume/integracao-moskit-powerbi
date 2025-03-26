package com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.api;

import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.MovimentacaoDto;

import java.util.List;

public interface MovimentacaoGatewayApi {
    List<MovimentacaoDto> consultaMovimentacoes(Integer id);
}

