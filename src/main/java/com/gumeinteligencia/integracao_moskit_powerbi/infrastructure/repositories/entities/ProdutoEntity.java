package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "Produto")
@Table(name = "produtos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProdutoEntity {

    @Id
    @Column(name = "id_produto")
    private Integer id;
    private String name;
    private String description;
    private Boolean active;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuarioEntity createdBy;
}
