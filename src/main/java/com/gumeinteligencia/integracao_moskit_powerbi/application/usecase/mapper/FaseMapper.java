package com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.mapper;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Fase;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.FaseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FaseMapper {

    private final FunilMapper funilMapper;

    public Fase paraDomain(FaseDto dto) {
        return Fase.builder()
                .id(dto.getId())
                .name(dto.getName())
                .pipeline(funilMapper.paraDomain(dto.getPipeline()))
                .build();
    }

    public FaseDto paraDto(Fase domain) {
        return FaseDto.builder()
                .id(domain.getId())
                .name(domain.getName())
                .pipeline(funilMapper.paraDto(domain.getPipeline()))
                .build();
    }
}
