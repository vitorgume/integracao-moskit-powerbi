package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity(name = "Empresa")
@Table(name = "empresas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmpresaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_empresa")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @Column(name = "created_by")
    private UsuarioEntity createdBy;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuarioEntity responsible;

    private String name;

    private String cnpj;
}
