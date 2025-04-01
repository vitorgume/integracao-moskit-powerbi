package com.gumeinteligencia.integracao_moskit_powerbi.entrypoint.controller;

import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.NegocioUseCase;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.NegocioDto;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.mapper.NegocioMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Negocio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("negocios")
@RequiredArgsConstructor
public class NegocioController {

    private final NegocioUseCase useCase;
    private final NegocioMapper mapper;

    @PostMapping
    public ResponseEntity<NegocioDto> atualizaStatus(@RequestBody NegocioDto novoNegocio) {
        Negocio negocioAtualizado = useCase.atualizaNegocio(mapper.paraDomain(novoNegocio));
        return ResponseEntity.ok(mapper.paraDto(negocioAtualizado));
    }

}
