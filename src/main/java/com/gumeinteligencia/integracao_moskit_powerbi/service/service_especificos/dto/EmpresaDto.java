package com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto;

import lombok.Builder;

@Builder
public record EmpresaDto(Integer id, UsuarioDto createdBy, UsuarioDto responsible, String name, String cnpj) {}
