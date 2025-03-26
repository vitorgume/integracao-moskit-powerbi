package com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MovimentacaoDto {
    private Integer id;
    private String dateCreated;
    private FaseDto currentStage;
    private FaseDto oldStage;
    private NegocioDto deal;
    private Boolean firstNavigation;
}
