package com.gumeinteligencia.integracao_moskit_powerbi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class Empresa {
    private UUID id;
    private Usuario createdBy;
    private Usuario responsible;
    private String name;
    private String cnpj;
}
