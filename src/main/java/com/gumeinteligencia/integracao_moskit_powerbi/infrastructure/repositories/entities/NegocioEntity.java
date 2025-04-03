package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.entities;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.segmento.Segmento;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.StatusNegocio;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

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

    @Column(name = "close_Date")
    private LocalDate closeDate;

    @Column(name = "motivo_perda")
    private String motivoPerda;

    @Enumerated(EnumType.STRING)
    private Segmento segmento;
}