package com.gumeinteligencia.integracao_moskit_powerbi.domain;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Atividade {
    private Integer id;
    private String title;
    private Usuario createdBy;
    private Usuario responsible;
    private Usuario doneUser;
    private LocalDate dateCreated;
    private LocalDate dueDate;
    private LocalDate doneDate;
    private TipoAtividade type;
    private Integer duration;
    private Integer totalTries;
    private List<Negocio> deals;
    private List<Empresa> companies;
}
