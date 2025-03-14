package com.gumeinteligencia.integracao_moskit_powerbi.mapper;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Fase;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.entity.FaseEntity;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.FaseDto;

public class FaseMapper {

    public static Fase paraDomain(FaseEntity entity) {
        return Fase.builder()
                .id(entity.getId())
                .name(entity.getName())
                .pipeline(FunilMapper.paraDomain(entity.getPipeline()))
                .build();
    }

    public static FaseEntity paraEntity(Fase domain) {
        return FaseEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .pipeline(FunilMapper.paraEntity(domain.getPipeline()))
                .build();
    }

    public static Fase paraDomainDeDto(FaseDto dto) {
        return Fase.builder()
                .id(dto.id())
                .name(dto.name())
                .pipeline(FunilMapper.paraDomainDeDto(dto.pipeline()))
                .build();
    }
}
