package com.gumeinteligencia.integracao_moskit_powerbi.domain;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Produto {

    private Integer id;
    private String name;
    private String description;
    private Boolean active;
    private BigDecimal price;
    private Usuario createdBy;
}
