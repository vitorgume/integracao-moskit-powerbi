package com.gumeinteligencia.integracao_moskit_powerbi.domain;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Funil {
    private Integer id;
    private String name;
}
