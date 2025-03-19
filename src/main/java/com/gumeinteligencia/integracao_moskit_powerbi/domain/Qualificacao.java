package com.gumeinteligencia.integracao_moskit_powerbi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Qualificacao {
    CONGELADA(424095),
    FRIA(424096),
    MORNA(424097),
    QUENTE(424099),
    MUITO_QUENTE(424098),
    SEM_QUALIFICACAO(0);

    private final Integer codigo;
}
