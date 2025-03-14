package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity(name = "Fase")
@Table(name = "fases")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FaseEntity {

    @Id
    @Column(name = "id_fase")
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "id_funil", nullable = false)
    private FunilEntity pipeline;
}
