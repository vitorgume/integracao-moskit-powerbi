package com.gumeinteligencia.integracao_moskit_powerbi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TipoAtividade {
    REUNIAO_PRESENCIAL(154678),
    REUNIAO_VIDEOCONFERENCIA(154679),
    DESCARTE(154865),
    FOLLOW_UP(155075),
    LIGACAO(155076),
    MENSAGEM(155077),
    COLOCAR_REUNIAO_NA_AGENDA_DO_CLOSER(155079),
    RETOMADA_DE_CONTATO(208746),
    SEM_QUALIFICACAO(0);

    private final Integer codigo;
}
