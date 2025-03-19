package com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MovimentacoesNegociosDto {
    private Integer id;
    private String dateCreated;
    private FaseDto currentStage;
    private FaseDto oldStage;
    private NegocioDto deal;
    private Boolean firstNavigation;
}
