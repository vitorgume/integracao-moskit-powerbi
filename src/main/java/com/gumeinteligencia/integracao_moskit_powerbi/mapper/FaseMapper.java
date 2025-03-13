package com.gumeinteligencia.integracao_moskit_powerbi.mapper;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Fase;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.entity.FaseEntity;

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
}
