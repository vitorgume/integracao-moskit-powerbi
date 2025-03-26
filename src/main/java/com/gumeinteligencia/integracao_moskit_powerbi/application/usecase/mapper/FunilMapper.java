package com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.mapper;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Funil;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.FunilDto;

public class FunilMapper {

    public static Funil paraDomain(FunilDto dto) {
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
