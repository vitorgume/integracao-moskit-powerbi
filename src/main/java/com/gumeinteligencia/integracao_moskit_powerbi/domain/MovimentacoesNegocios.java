package com.gumeinteligencia.integracao_moskit_powerbi.domain;

import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.FaseDto;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.NegocioDto;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MovimentacoesNegocios {
    private Integer id;
    private LocalDate dataCriacao;
    private Fase faseAtual;
    private Fase faseAntiga;
    private Negocio negocio;
    private Boolean primeiraNavegacao;
}
