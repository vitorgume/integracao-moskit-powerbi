package com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MovimentacaoNegociosDto {
    private Integer id;
    private String dateCreated;
    private FaseDto currentStage;
    private FaseDto oldStage;
    private NegocioDto deal;
    private Boolean firstNavigation;
}
