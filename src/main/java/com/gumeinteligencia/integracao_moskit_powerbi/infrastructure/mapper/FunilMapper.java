package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.mapper;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Funil;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.entities.FunilEntity;

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
}
