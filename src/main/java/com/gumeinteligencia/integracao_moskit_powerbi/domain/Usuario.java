package com.gumeinteligencia.integracao_moskit_powerbi.domain;


import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class Usuario {

    private Integer id;

    @EqualsAndHashCode.Include
    private String name;

    private String userName;

    private Boolean active;
}
