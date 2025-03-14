package com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.StatusNegocio;

import java.math.BigDecimal;

public record NegocioDto(Integer id, UsuarioDto createdBy, UsuarioDto responsible,
                         String name, BigDecimal price, StatusNegocio status, FaseDto stage) {}
