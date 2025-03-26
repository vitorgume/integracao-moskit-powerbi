package com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FaseDto {
    private Integer id;
    private String name;
    private FunilDto pipeline;
}