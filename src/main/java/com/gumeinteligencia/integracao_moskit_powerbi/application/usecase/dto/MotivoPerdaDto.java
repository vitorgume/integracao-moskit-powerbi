package com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class MotivoPerdaDto {
    private Integer id;
    private String name;
}
