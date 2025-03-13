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
public class Usuario {
    private UUID id;
    private String name;
    private String userName;
    private Boolean active;
}
