package com.gumeinteligencia.integracao_moskit_powerbi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusNegocio {
    OPEN(0, "Aberto"),
    WON(1, "Ganho"),
    LOST(2, "Perdido");

    private final int codigo;
    private final String descricao;
}
