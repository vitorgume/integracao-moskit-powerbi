package com.gumeinteligencia.integracao_moskit_powerbi.application.usecase;

import com.gumeinteligencia.integracao_moskit_powerbi.application.exceptions.MotivoPerdaNaoEncontradoException;
import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.api.MotivoPerdaGatewayApi;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.MotivoPerdaDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MotivoPerdaUseCase {

    private final MotivoPerdaGatewayApi gatewayApi;


    public MotivoPerdaDto consultarMotivo(Integer id) {
        log.info("Consultando motivo de perda pelo seu id. Id: {}", id);

        Optional<MotivoPerdaDto> motivoPerdaOptional = gatewayApi.consultaMotivoPerda(id);

        if(motivoPerdaOptional.isEmpty()) {
            throw new MotivoPerdaNaoEncontradoException(id);
        }

        MotivoPerdaDto motivoPerda = motivoPerdaOptional.get();

        log.info("Motivo de perda consultado com sucesso. Motivo: {}", motivoPerda);

        return motivoPerda;
    }
}
