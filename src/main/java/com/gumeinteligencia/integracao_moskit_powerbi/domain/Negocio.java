package com.gumeinteligencia.integracao_moskit_powerbi.domain;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Negocio {
    private Integer id;
    private Usuario createdBy;
    private Usuario responsible;
    private String name;
    private BigDecimal price;
    private StatusNegocio status;
    private Fase stage;
    private LocalDate dateCreated;
    private LocalDate closeDate;
    private String motivoPerda;
    private Segmento segmento;

    public void atualizaDados(Negocio novosDados) {
        this.name = novosDados.getName();
        this.price = novosDados.getPrice();
        this.status = novosDados.getStatus();
        this.stage = novosDados.getStage();
        this.closeDate = novosDados.getCloseDate();
        this.motivoPerda = novosDados.getMotivoPerda();
        this.segmento = novosDados.getSegmento();
    }
}