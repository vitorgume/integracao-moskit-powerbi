package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioEntity {

    @Id
    @Column(name = "id_usuario")
    private Integer id;
    private String name;
    private String userName;
    private Boolean active;
}
