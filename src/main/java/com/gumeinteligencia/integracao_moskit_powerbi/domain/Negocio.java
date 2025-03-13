package com.gumeinteligencia.integracao_moskit_powerbi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class Negocio {
    private UUID id;
    private Usuario createdBy;
    private Usuario responsible;
    private String name;
    private BigDecimal price;
    private StatusNegocio status;
    private Fase stage;
}
