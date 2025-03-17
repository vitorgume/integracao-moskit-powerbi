package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.entity;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.StatusNegocio;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    @Column(name = "id_negocio")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_usuario_created", nullable = false)
    private UsuarioEntity createdBy;

    @ManyToOne
    @JoinColumn(name = "id_usuario_responsible", nullable = false)
    private UsuarioEntity responsible;

    private String name;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private StatusNegocio status;

    @ManyToOne
    @JoinColumn(name = "id_fase", nullable = false)
    private FaseEntity stage;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "prevision_close_date")
    private LocalDate previsionCloseDate;

    @Column(name = "close_Date")
    private LocalDate closeDate;
}
