package com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto;

import lombok.Builder;

@Builder
public record UsuarioDto(Integer id, String name, String userName, Boolean active) {
}
