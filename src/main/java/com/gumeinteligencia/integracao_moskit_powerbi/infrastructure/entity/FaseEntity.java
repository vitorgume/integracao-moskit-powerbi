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
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_fase")
    private UUID id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "id_funil", nullable = false)
    private FunilEntity pipeline;
}
