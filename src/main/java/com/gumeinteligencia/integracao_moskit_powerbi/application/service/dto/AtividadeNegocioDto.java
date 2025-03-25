package com.gumeinteligencia.integracao_moskit_powerbi.application.service.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AtividadeNegocioDto {
    private Integer id;
    private String title;
    private UsuarioDto createdBy;
    private UsuarioDto responsible;
    private UsuarioDto doneUser;
    private String dateCreated;
    private String dueDate;
    private String doneDate;
    private TipoAtividadeDto type;
    private Integer duration;
    private Integer totalTries;
    private List<NegocioDto> deals;
    private List<EmpresaDto> companies;
}
