package com.gumeinteligencia.integracao_moskit_powerbi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public enum Segmento {
    EDUCACAO(475021, "Educação"),
    INDUSTRI(475022, "Indústria"),
    TECNOLOGIA(475023, "Tecnologia"),
    VAREJO(475024, "Varejo"),
    PRESTADORES_DE_SERVICE(475025, "Prestadores de serviço"),
    AGENCIA_MARKETING(479747, "Agência de Marketing"),
    JOGOS(479748, "Jogos"),
    CLINICA_MEDICA(523867, "Clínica Médica"),
    SEM_SEGMENTO(0, "Sem segmento");

    private Integer codigo;
    private String descricao;
}
