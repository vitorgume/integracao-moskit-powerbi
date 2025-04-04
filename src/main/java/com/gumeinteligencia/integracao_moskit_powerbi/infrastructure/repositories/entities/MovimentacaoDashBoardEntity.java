package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "MovimentacaoDashBoard")
@Table(name = "movimentacao_dash_board")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MovimentacaoDashBoardEntity {

    @Id
    @Column(name = "id_movitacoes_negocios")
    private Integer id;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    @ManyToOne
    @JoinColumn(name = "id_fase_atual", nullable = false)
    private FaseEntity faseAtual;

    @ManyToOne
    @JoinColumn(name = "id_fase_antiga")
    private FaseEntity faseAntiga;

    @ManyToOne
    @JoinColumn(name = "id_negocios", nullable = false)
    private NegocioEntity negocio;

    private Boolean primeiraNavegacao;
}
