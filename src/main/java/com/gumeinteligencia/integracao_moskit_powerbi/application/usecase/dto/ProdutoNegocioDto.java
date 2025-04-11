package com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ProdutoNegocioDto {
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal finalPrice;
    private BigDecimal initialPrice;
    private ProdutoDto product;
}
