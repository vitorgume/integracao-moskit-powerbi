package com.gumeinteligencia.integracao_moskit_powerbi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class MotivoPerda {
    private Integer id;
    private String name;
}
