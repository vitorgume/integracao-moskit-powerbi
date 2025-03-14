package com.gumeinteligencia.integracao_moskit_powerbi.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Negocio {
    private Integer id;
    private Usuario createdBy;
    private Usuario responsible;
    private String name;
    private BigDecimal price;
    private StatusNegocio status;
    private Fase stage;
}
