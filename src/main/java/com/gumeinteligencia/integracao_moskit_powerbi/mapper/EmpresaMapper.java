package com.gumeinteligencia.integracao_moskit_powerbi.mapper;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Empresa;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.entity.EmpresaEntity;

public class EmpresaMapper {

    public static Empresa paraDomain(EmpresaEntity entity) {
        return Empresa.builder()
                .id(entity.getId())
                .cnpj(entity.getCnpj())
                .name(entity.getName())
                .responsible(UsuarioMapper.paraDomain(entity.getResponsible()))
                .createdBy(UsuarioMapper.paraDomain(entity.getCreatedBy()))
                .build();
    }

    public static EmpresaEntity paraEntity(Empresa domain) {
        return EmpresaEntity.builder()
                .id(domain.getId())
                .cnpj(domain.getCnpj())
                .name(domain.getName())
                .responsible(UsuarioMapper.paraEntity(domain.getResponsible()))
                .createdBy(UsuarioMapper.paraEntity(domain.getCreatedBy()))
                .build();
    }
}
