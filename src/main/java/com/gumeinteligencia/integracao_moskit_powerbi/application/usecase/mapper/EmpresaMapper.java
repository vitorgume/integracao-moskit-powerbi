package com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.mapper;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Empresa;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.EmpresaDto;

public class EmpresaMapper {

    public static Empresa paraDomain(EmpresaDto dto) {
        return Empresa.builder()
                .id(dto.id())
                .cnpj(dto.cnpj())
                .name(dto.name())
                .responsible(UsuarioMapper.paraDomain(dto.responsible()))
                .createdBy(UsuarioMapper.paraDomain(dto.createdBy()))
                .build();
    }

    public static EmpresaDto paraDto(Empresa empresa) {
        return EmpresaDto.builder()
                .id(empresa.getId())
                .cnpj(empresa.getCnpj())
                .name(empresa.getName())
                .responsible(UsuarioMapper.paraDto(empresa.getResponsible()))
                .createdBy(UsuarioMapper.paraDto(empresa.getCreatedBy()))
                .build();
    }
}
