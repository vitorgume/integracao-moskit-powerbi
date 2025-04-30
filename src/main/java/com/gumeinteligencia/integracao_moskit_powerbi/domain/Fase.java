package com.gumeinteligencia.integracao_moskit_powerbi.domain;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Fase {
    private Integer id;
    private String name;
    private Funil pipeline;
}
