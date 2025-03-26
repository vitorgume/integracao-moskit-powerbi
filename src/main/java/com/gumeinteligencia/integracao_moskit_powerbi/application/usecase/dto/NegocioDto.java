package com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Qualificacao;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.StatusNegocio;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class NegocioDto {
    private Integer id;
    private UsuarioDto createdBy;
    private UsuarioDto responsible;
    private String name;
    private BigDecimal price;
    private StatusNegocio status;
    private FaseDto stage;
    private String dateCreated;
    private String closeDate;
    private List<CampoPersonalizadoDto> entityCustomFields;
    private Qualificacao qualificacao;
}