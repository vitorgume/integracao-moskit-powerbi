package com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ProdutoDto {
    private Integer id;
    private String name;
    private String description;
    private Boolean active;
    private BigDecimal price;
    private UsuarioDto createdBy;
}
