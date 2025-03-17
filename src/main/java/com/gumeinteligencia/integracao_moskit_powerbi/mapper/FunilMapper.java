package com.gumeinteligencia.integracao_moskit_powerbi.mapper;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Funil;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.entity.FunilEntity;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.FunilDto;

public class FunilMapper {

    public static Funil paraDomain(FunilEntity entity) {
        return Funil.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public static FunilEntity paraEntity(Funil domain) {
        return FunilEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .build();
    }

    public static Funil paraDomainDeDto(FunilDto dto) {
        return Funil.builder()
                .id(dto.id())
                .name(dto.name())
                .build();
    }

    public static FunilDto paraDto(Funil funilStage) {
        return FunilDto.builder()
                .id(funilStage.getId())
                .name(funilStage.getName())
                .build();
    }
}
