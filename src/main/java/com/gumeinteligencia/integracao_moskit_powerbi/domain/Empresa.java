package com.gumeinteligencia.integracao_moskit_powerbi.domain;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Empresa {
    private Integer id;
    private Usuario createdBy;
    private Usuario responsible;
    private String name;
    private String cnpj;
}
