package com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.StatusNegocio;

import java.math.BigDecimal;
import java.util.List;

public record NegocioDto(Integer id, UsuarioDto createdBy, UsuarioDto responsible,
                         String name, BigDecimal price, StatusNegocio status, FaseDto stage,
                         String dateCreated, String previsionCloseDate, String closeDate,
                         List<CampoPersonalizadoDto> entityCustomFields) {}