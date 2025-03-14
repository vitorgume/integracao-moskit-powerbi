package com.gumeinteligencia.integracao_moskit_powerbi.mapper;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Negocio;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.entity.NegocioEntity;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.NegocioDto;

public class NegocioMapper {

    public static Negocio paraDomain(NegocioEntity entity) {
        return Negocio.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .stage(FaseMapper.paraDomain(entity.getStage()))
                .status(entity.getStatus())
                .responsible(UsuarioMapper.paraDomain(entity.getResponsible()))
                .createdBy(UsuarioMapper.paraDomain(entity.getCreatedBy()))
                .build();
    }

    public static NegocioEntity paraEntity(Negocio domain) {
        return NegocioEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .price(domain.getPrice())
                .stage(FaseMapper.paraEntity(domain.getStage()))
                .status(domain.getStatus())
                .responsible(UsuarioMapper.paraEntity(domain.getResponsible()))
                .createdBy(UsuarioMapper.paraEntity(domain.getCreatedBy()))
                .build();
    }

//    public static Negocio paraDomainDeDto(NegocioDto dto) {
//        return Negocio.builder()
//                .name(dto.name())
//                .price(dto.price())
//                .stage(dto.stage())
//                .status(dto)
//    }
}
