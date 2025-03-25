package com.gumeinteligencia.integracao_moskit_powerbi.application.service.dto;

import lombok.Builder;

@Builder
public record UsuarioDto(Integer id, String name, String userName, Boolean active) {
}
