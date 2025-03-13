package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity(name = "Funil")
@Table(name = "funis")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FunilEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_funil")
    private UUID id;
    private String name;
}
