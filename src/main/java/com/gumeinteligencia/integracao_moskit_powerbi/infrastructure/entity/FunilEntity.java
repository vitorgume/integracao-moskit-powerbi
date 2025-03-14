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
    @Column(name = "id_funil")
    private Integer id;
    private String name;
}
