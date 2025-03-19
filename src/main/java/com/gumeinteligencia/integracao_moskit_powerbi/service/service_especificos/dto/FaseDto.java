package com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto;

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