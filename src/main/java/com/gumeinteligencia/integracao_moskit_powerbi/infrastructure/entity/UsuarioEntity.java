package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String userName;
    private Boolean active;
}
