package com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//public record FaseDto(Integer id, String name, FunilDto pipeline) {}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FaseDto {
    private Integer id;
    private String name;
    private FunilDto pipeline;
}