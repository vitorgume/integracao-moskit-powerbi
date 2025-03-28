package com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.api;

import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.MotivoPerdaDto;

import java.util.Optional;

public interface MotivoPerdaGatewayApi {
    Optional<MotivoPerdaDto> consultaMotivoPerda(Integer id);
}
