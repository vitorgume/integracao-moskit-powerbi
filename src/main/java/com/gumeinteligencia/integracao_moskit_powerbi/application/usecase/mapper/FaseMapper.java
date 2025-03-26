package com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.mapper;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Fase;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.FaseDto;

public class FaseMapper {

    public static Fase paraDomain(FaseDto dto) {
        return Fase.builder()
                .id(dto.getId())
                .name(dto.getName())
                .pipeline(FunilMapper.paraDomain(dto.getPipeline()))
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
