package com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Usuario;

public record EmpresaDto(Integer id, UsuarioDto createdBy, UsuarioDto responsible, String name, String cnpj) {}
