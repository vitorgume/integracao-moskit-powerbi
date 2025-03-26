package com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.mapper;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Negocio;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.NegocioDto;

public class NegocioMapper {

    public static Negocio paraDomain(NegocioDto dto) {
        return Negocio.builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .stage(FaseMapper.paraDomain(dto.getStage()))
                .status(dto.getStatus())
                .responsible(UsuarioMapper.paraDomain(dto.getResponsible()))
                .createdBy(UsuarioMapper.paraDomain(dto.getCreatedBy()))
                .dateCreated(MapperData.trasnformaData(dto.getDateCreated()))
                .closeDate(MapperData.trasnformaData(dto.getCloseDate()))
                .qualificacao(MapperEnum.organizaQualificacao(dto))
                .build();
    }

    public static NegocioDto paraDto(Negocio domain) {
        if(domain.getCloseDate() == null) {
            return NegocioDto.builder()
                    .id(domain.getId())
                    .name(domain.getName())
                    .price(domain.getPrice())
                    .stage(FaseMapper.paraDto(domain.getStage()))
                    .status(domain.getStatus())
                    .responsible(UsuarioMapper.paraDto(domain.getResponsible()))
                    .createdBy(UsuarioMapper.paraDto(domain.getCreatedBy()))
                    .dateCreated(MapperData.trasformaDataString(domain.getDateCreated()))
                    .qualificacao(domain.getQualificacao())
                    .build();
        } else {
            return NegocioDto.builder()
                    .id(domain.getId())
                    .name(domain.getName())
                    .price(domain.getPrice())
                    .stage(FaseMapper.paraDto(domain.getStage()))
                    .status(domain.getStatus())
                    .responsible(UsuarioMapper.paraDto(domain.getResponsible()))
                    .createdBy(UsuarioMapper.paraDto(domain.getCreatedBy()))
                    .dateCreated(MapperData.trasformaDataString(domain.getDateCreated()))
                    .closeDate(MapperData.trasformaDataString(domain.getCloseDate()))
                    .qualificacao(domain.getQualificacao())
                    .build();
        }


    }



}