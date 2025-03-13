package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.entity;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.StatusNegocio;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity(name = "Negocio")
@Table(name = "negocios")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class NegocioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_negocio")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @Column(name = "created_by")
    private UsuarioEntity createdBy;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuarioEntity responsible;

    private String name;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private StatusNegocio status;

    @ManyToOne
    @JoinColumn(name = "id_fase", nullable = false)
    private FaseEntity stage;
}
