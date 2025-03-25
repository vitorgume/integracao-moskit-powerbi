package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Empresa")
@Table(name = "empresas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmpresaEntity {

    @Id
    @Column(name = "id_empresa")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_usuario_created", nullable = false)
    private UsuarioEntity createdBy;

    @ManyToOne
    @JoinColumn(name = "id_usuario_responsible", nullable = false)
    private UsuarioEntity responsible;

    private String name;

    private String cnpj;
}
