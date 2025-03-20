package com.gumeinteligencia.integracao_moskit_powerbi.domain;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MovimentacaoNegocio {
    private Integer id;
    private LocalDate dataCriacao;
    private Fase faseAtual;
    private Fase faseAntiga;
    private Negocio negocio;
    private Boolean primeiraNavegacao;
}
