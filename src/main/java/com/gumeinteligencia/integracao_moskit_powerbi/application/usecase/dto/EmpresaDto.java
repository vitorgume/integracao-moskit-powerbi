package com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto;

import lombok.Builder;

@Builder
public record EmpresaDto(Integer id, UsuarioDto createdBy, UsuarioDto responsible, String name, String cnpj) {}
