package com.gumeinteligencia.integracao_moskit_powerbi.mapper;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Fase;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.entities.FaseEntity;
import com.gumeinteligencia.integracao_moskit_powerbi.application.service.dto.FaseDto;

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
                .id(dto.getId())
                .name(dto.getName())
                .pipeline(FunilMapper.paraDomainDeDto(dto.getPipeline()))
                .build();
    }

    public static FaseDto paraDto(Fase domain) {
        return FaseDto.builder()
                .id(domain.getId())
                .name(domain.getName())
                .pipeline(FunilMapper.paraDto(domain.getPipeline()))
                .build();
    }
}
