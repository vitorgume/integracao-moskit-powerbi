package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.entity;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.TipoAtividade;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "AtividadesNegocio")
@Table(name = "atividades_negocio")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AtividadeNegocioEntity {

    @Id
    @Column(name = "id_atividade")
    private Integer id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "id_usuario_created", nullable = false)
    private UsuarioEntity createdBy;

    @ManyToOne
    @JoinColumn(name = "id_usuario_responsible", nullable = false)
    private UsuarioEntity responsible;

    @ManyToOne
    @JoinColumn(name = "id_usuario_done", nullable = false)
    private UsuarioEntity doneUser;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "done_date")
    private LocalDate doneDate;

    private TipoAtividade type;

    private Integer duration;

    @Column(name = "total_tries")
    private Integer totalTries;

    @ManyToMany
    @JoinTable(
            name = "negocios_atividade",
            joinColumns = @JoinColumn(name = "id_atividade", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "id_negocio", nullable = false)
    )
    private List<NegocioEntity> deals;

    @ManyToMany
    @JoinTable(
            name = "empresas_atividade",
            joinColumns = @JoinColumn(name = "id_atividade", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "id_empresa", nullable = false)
    )
    private List<EmpresaEntity> companies;
}
